package model;

import been.RefeicaoBean;
import model.AlimentoQuantidade;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import java.util.List;

public class RefeicaoModel {

    // Inserir refeição e associar alimentos
    public static boolean inserirRefeicaoComAlimentos(Driver driver, RefeicaoBean refeicao) {
        try (Session session = driver.session()) {
            
            session.writeTransaction(tx -> {
                // Criar refeição
                String createRefeicao = """
                    CREATE (r:Refeicao {nome: $nome, descricao: $descricao})
                    RETURN id(r) AS idRefeicao
                """;

                Result result = tx.run(createRefeicao, Values.parameters(
                        "nome", refeicao.getNome(),
                        "descricao", refeicao.getDescricao()
                ));

                long idRefeicao = result.single().get("idRefeicao").asLong();

                // Relacionar alimentos à refeição
                for (AlimentoQuantidade aq : refeicao.getAlimentos()) {
                    String linkAlimento = """
                        MATCH (r:Refeicao), (a:alimento {id_alimento: $idAlimento})
                        WHERE id(r) = $idRefeicao
                        MERGE (r)-[:INCLUI {quantidade: $quantidade}]->(a)
                    """;
                    tx.run(linkAlimento, Values.parameters(
                            "idRefeicao", idRefeicao,
                            "idAlimento", aq.getAlimento().getIdAlimento(),
                            "quantidade", aq.getQuantidade()
                    ));
                }

                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Listar refeições com detalhes e total de nutrientes
    public static void listarRefeicoesComDetalhes(Driver driver) {
        try (Session session = driver.session()) {
            String query = """
                MATCH (r:Refeicao)-[rel:INCLUI]->(a:alimento)
                RETURN id(r) AS idRefeicao, r.nome AS nome, r.descricao AS descricao,
                       a.id_alimento AS idAlimento, a.nome_alimento AS nomeAlimento,
                       rel.quantidade AS quantidade,
                       a.calorias AS calorias,
                       a.proteinas AS proteinas,
                       a.carboidratos AS carboidratos
                ORDER BY idRefeicao
            """;

            session.readTransaction(tx -> {
                Result rs = tx.run(query);

                long idRefeicaoAtual = -1;
                String nomeRefeicao = "", descricao = "";
                double totalCalorias = 0, totalProteinas = 0, totalCarboidratos = 0;
                List<String> alimentos = new java.util.ArrayList<>();

                while (rs.hasNext()) {
                    org.neo4j.driver.Record row = rs.next();
                    long idRefeicao = row.get("idRefeicao").asLong();

                    if (idRefeicao != idRefeicaoAtual && idRefeicaoAtual != -1) {
                        // Imprimir anterior
                        System.out.println("Nome da Refeição: " + nomeRefeicao);
                        System.out.println("Descrição: " + descricao);
                        System.out.println("Total Calorias: " + totalCalorias);
                        System.out.println("Total Proteínas: " + totalProteinas);
                        System.out.println("Total Carboidratos: " + totalCarboidratos);
                        for (String alimento : alimentos) System.out.println(alimento);
                        System.out.println("--------------------------------------------------");

                        totalCalorias = 0;
                        totalProteinas = 0;
                        totalCarboidratos = 0;
                        alimentos.clear();
                    }

                    idRefeicaoAtual = idRefeicao;
                    nomeRefeicao = row.get("nome").asString();
                    descricao = row.get("descricao").asString();

                    double qtd = row.get("quantidade").asDouble();
                    totalCalorias += row.get("calorias").asDouble() * qtd;
                    totalProteinas += row.get("proteinas").asDouble() * qtd;
                    totalCarboidratos += row.get("carboidratos").asDouble() * qtd;

                    String alimentoInfo = String.format("ID Alimento: %d  Nome Alimento: %s",
                            row.get("idAlimento").asInt(),
                            row.get("nomeAlimento").asString());
                    alimentos.add(alimentoInfo);
                }

                if (idRefeicaoAtual != -1) {
                    System.out.println("Nome da Refeição: " + nomeRefeicao);
                    System.out.println("Descrição: " + descricao);
                    System.out.println("Total Calorias: " + totalCalorias);
                    System.out.println("Total Proteínas: " + totalProteinas);
                    System.out.println("Total Carboidratos: " + totalCarboidratos);
                    for (String alimento : alimentos) System.out.println(alimento);
                    System.out.println("--------------------------------------------------");
                }

                return null;
            });
        }
    }

    // Excluir uma refeição (se não estiver vinculada a nenhum plano)
    public static boolean excluirRefeicao(Driver driver, String nomeRefeicao) {
        try (Session session = driver.session()) {
            return session.writeTransaction(tx -> {
                // Verifica vínculo com plano
                String checkVinculo = """
                    MATCH (:PlanoAlimentar)-[:CONTEM]->(r:Refeicao)
                    WHERE r.nome = $nomeRefeicao
                    RETURN COUNT(*) AS total
                """;
                Result res = tx.run(checkVinculo, Values.parameters("nomeRefeicao", nomeRefeicao));
                int total = res.single().get("total").asInt();

                if (total > 0) return false;

                // Deleta a refeição e os relacionamentos
                String delete = """
                    MATCH (r:Refeicao)
                    WHERE r.nome = $nomeRefeicao
                    DETACH DELETE r
                """;
                tx.run(delete, Values.parameters("nomeRefeicao", nomeRefeicao));
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

package model;

import been.PlanoAlimentarBean;
import org.neo4j.driver.*;

public class PlanoAlimentarModel {

    // Inserir um plano alimentar e suas refeições
    public static boolean inserirPlanoAlimentar(Driver driver, PlanoAlimentarBean planoAlimentar) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                String createPlanoQuery = "CREATE (p:PlanoAlimentar {descricao: $descricao}) RETURN id(p) AS planoId";
                Result result = tx.run(createPlanoQuery, Values.parameters("descricao", planoAlimentar.getDescricao()));
                long planoId = result.single().get("planoId").asLong();

                for (RefeicaoHorario rh : planoAlimentar.getRefeicoes()) {
                    // Criar relacionamento com refeição existente
                    String relQuery = """
                        MATCH (p:PlanoAlimentar), (r:Refeicao)
                        WHERE id(p) = $planoId AND r.nome = $nomeRefeicao
                        MERGE (p)-[:CONTEM {horario: $horario}]->(r)
                        """;
                    tx.run(relQuery, Values.parameters(
                            "planoId", planoId,
                            "nomeRefeicao", rh.getNomeRefeicao(),
                            "horario", rh.getHoraRefeicao().toString()
                    ));
                }

                return null;
            });
        }
        return true;
    }

    // Listar todos os planos alimentares com detalhes
    public static void listarPlanoAlimentarComDetalhes(Driver driver) {
        try (Session session = driver.session()) {
            String query = """
                MATCH (p:PlanoAlimentar)-[c:CONTEM]->(r:Refeicao)
                OPTIONAL MATCH (r)-[i:INCLUI]->(a:Alimento)
                RETURN id(p) AS planoId, p.descricao AS planoDescricao,
                       id(r) AS refeicaoId, r.nome AS nomeRefeicao, r.descricao AS descricaoRefeicao,
                       c.horario AS horario,
                       a.nome AS nomeAlimento, i.quantidade AS quantidade
                ORDER BY planoId, refeicaoId
            """;

            session.readTransaction(tx -> {
                Result rs = tx.run(query);
                long planoAtual = -1;
                long refeicaoAtual = -1;

                while (rs.hasNext()) {
                    org.neo4j.driver.Record row = rs.next(); 
                    long planoId = row.get("planoId").asLong();
                    long refeicaoId = row.get("refeicaoId").asLong();

                    if (planoId != planoAtual) {
                        planoAtual = planoId;
                        System.out.println("Plano ID: " + planoId);
                        System.out.println("Descrição do Plano: " + row.get("planoDescricao").asString());
                    }

                    if (refeicaoId != refeicaoAtual) {
                        refeicaoAtual = refeicaoId;
                        System.out.println("  Refeição ID: " + refeicaoId);
                        System.out.println("  Nome da Refeição: " + row.get("nomeRefeicao").asString());
                        System.out.println("  Descrição: " + row.get("descricaoRefeicao").asString());
                        System.out.println("  Horário: " + row.get("horario").asString());
                        System.out.println("  Alimentos:");
                    }

                    if (!row.get("nomeAlimento").isNull()) {
                        System.out.printf("    - %s (quantidade: %.2f)%n",
                                row.get("nomeAlimento").asString(),
                                row.get("quantidade").asDouble());
                    }
                }
                return null;
            });
        }
    }

public static void listarPlanoAlimentarPorUsuario(Driver driver, String nomeUsuario) {
    try (Session session = driver.session()) {
        String query = """
            MATCH (u:Usuario {nome: $nomeUsuario})-[:TEM_PLANO]->(p:PlanoAlimentar)-[c:CONTEM]->(r:Refeicao)
            OPTIONAL MATCH (r)-[i:INCLUI]->(a:Alimento)
            RETURN p.descricao AS planoDescricao,
                   r.nome AS nomeRefeicao, r.descricao AS descricaoRefeicao,
                   c.horario AS horario,
                   a.nome AS nomeAlimento, i.quantidade AS quantidade
            ORDER BY planoDescricao, nomeRefeicao
        """;

        session.readTransaction(tx -> {
            Result rs = tx.run(query, Values.parameters("nomeUsuario", nomeUsuario));
            if (!rs.hasNext()) {
                System.out.println("Você não tem plano alimentar cadastrado, entre em contato com o seu nutricionista.");
                return null;
            }

            String planoAtual = "";
            String refeicaoAtual = "";

            while (rs.hasNext()) {
                org.neo4j.driver.Record row = rs.next();
                String planoDescricao = row.get("planoDescricao").asString();
                String nomeRefeicao = row.get("nomeRefeicao").asString();

                if (!planoDescricao.equals(planoAtual)) {
                    planoAtual = planoDescricao;
                    System.out.println("\nPlano: " + planoDescricao);
                }

                if (!nomeRefeicao.equals(refeicaoAtual)) {
                    refeicaoAtual = nomeRefeicao;
                    System.out.println("  Refeição: " + nomeRefeicao);
                    System.out.println("  Descrição: " + row.get("descricaoRefeicao").asString());
                    System.out.println("  Horário: " + row.get("horario").asString());
                    System.out.println("  Alimentos:");
                }

                if (!row.get("nomeAlimento").isNull()) {
                    System.out.printf("    - %s (quantidade: %.2f)%n",
                            row.get("nomeAlimento").asString(),
                            row.get("quantidade").asDouble());
                }
            }

            return null;
        });
    }
}

   public static boolean excluirPlanoAlimentar(Driver driver, String nomePlano) {
    try (Session session = driver.session()) {
        return session.writeTransaction(tx -> {
            // Verifica vínculo com usuário
            String checkQuery = """
                MATCH (p:PlanoAlimentar)<-[:TEM_PLANO]-(:Usuario)
                WHERE p.descricao = $nomePlano
                RETURN count(*) AS qtd
            """;
            Result res = tx.run(checkQuery, Values.parameters("nomePlano", nomePlano));
            int qtd = res.single().get("qtd").asInt();
            if (qtd > 0) return false;

            // Remove os relacionamentos e o plano
            String deleteQuery = """
                MATCH (p:PlanoAlimentar)
                WHERE p.descricao = $nomePlano
                DETACH DELETE p
            """;
            tx.run(deleteQuery, Values.parameters("nomePlano", nomePlano));
            return true;
        });
    } catch (Exception e) {
        System.err.println("Erro ao excluir plano alimentar: " + e.getMessage());
        return false;
    }
    }
}

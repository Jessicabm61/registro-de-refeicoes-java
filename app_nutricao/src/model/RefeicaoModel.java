
package model;

import been.RefeicaoBean;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import model.AlimentoQuantidade;


public class RefeicaoModel {
    public static boolean inserirRefeicaoComAlimentos(Connection con, RefeicaoBean refeicao) throws SQLException {
        String insertRefeicaoSQL = "INSERT INTO refeicao (nome_refeicao, descricao) VALUES (?, ?)";
        PreparedStatement pstRefeicao = con.prepareStatement(insertRefeicaoSQL, Statement.RETURN_GENERATED_KEYS);
        pstRefeicao.setString(1, refeicao.getNome());
        pstRefeicao.setString(2, refeicao.getDescricao());
        pstRefeicao.executeUpdate();

        ResultSet rs = pstRefeicao.getGeneratedKeys();
        int idRefeicao = 0;
        if (rs.next()) {
            idRefeicao = rs.getInt(1);
        }

        String insertAlimentoSQL = "INSERT INTO alimento_refeicao (id_refeicao, id_alimento, quantidade) VALUES (?, ?, ?)";
        PreparedStatement pstAlimento = con.prepareStatement(insertAlimentoSQL);

        for (AlimentoQuantidade aq : refeicao.getAlimentos()) {
            pstAlimento.setInt(1, idRefeicao);
            pstAlimento.setInt(2, aq.getAlimento().getIdAlimento());
            pstAlimento.setDouble(3, aq.getQuantidade());
            pstAlimento.addBatch();
        }

        pstAlimento.executeBatch();
        return true;
    }
    
    public static void listarRefeicoesComDetalhes(Connection con) throws SQLException {
        String sql = """
            SELECT 
                r.id_refeicao,
                r.nome_refeicao,
                r.descricao,
                a.id_alimento,
                a.nome_alimento,
                ar.quantidade,
                a.calorias,
                a.proteinas,
                a.carboidratos
            FROM refeicao r
            JOIN alimento_refeicao ar ON r.id_refeicao = ar.id_refeicao
            JOIN alimento a ON ar.id_alimento = a.id_alimento
            ORDER BY r.id_refeicao
            """;

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        int idRefeicaoAtual = -1;
        String nomeRefeicao = "", descricao = "";
        double totalCalorias = 0, totalProteinas = 0, totalCarboidratos = 0;

        List<String> alimentos = new ArrayList<>();

        while (rs.next()) {
            int idRefeicao = rs.getInt("id_refeicao");

            if (idRefeicao != idRefeicaoAtual && idRefeicaoAtual != -1) {
                // Imprimir dados da refeição anterior
                System.out.println("Nome da Refeição: " + nomeRefeicao);
                System.out.println("Descrição: " + descricao);
                System.out.println("Total Calorias: " + totalCalorias);
                System.out.println("Total Proteínas: " + totalProteinas);
                System.out.println("Total Carboidratos: " + totalCarboidratos);
                for (String alimento : alimentos) {
                    System.out.println(alimento);
                }
                System.out.println("--------------------------------------------------");

                // Resetar valores
                totalCalorias = 0;
                totalProteinas = 0;
                totalCarboidratos = 0;
                alimentos.clear();
            }

            idRefeicaoAtual = idRefeicao;
            nomeRefeicao = rs.getString("nome_refeicao");
            descricao = rs.getString("descricao");

            double qtd = rs.getDouble("quantidade");

            totalCalorias += rs.getDouble("calorias") * qtd;
            totalProteinas += rs.getDouble("proteinas") * qtd;
            totalCarboidratos += rs.getDouble("carboidratos") * qtd;

            String alimentoInfo = String.format(
                "ID Alimento: %d  Nome Alimento: %s",
                rs.getInt("id_alimento"),
                rs.getString("nome_alimento")
            );
            alimentos.add(alimentoInfo);
        }

        // Imprimir a última refeição
        if (idRefeicaoAtual != -1) {
            System.out.println("Nome da Refeição: " + nomeRefeicao);
            System.out.println("Descrição: " + descricao);
            System.out.println("Total Calorias: " + totalCalorias);
            System.out.println("Total Proteínas: " + totalProteinas);
            System.out.println("Total Carboidratos: " + totalCarboidratos);
            for (String alimento : alimentos) {
                System.out.println(alimento);
            }
            System.out.println("--------------------------------------------------");
        }
    }
}
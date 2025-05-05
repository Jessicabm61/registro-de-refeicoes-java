package model;

import java.sql.PreparedStatement;
import been.PlanoAlimentarBean;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;



public class PlanoAlimentarModel {
    public static boolean inserirPlanoAlimentar(Connection con, PlanoAlimentarBean planoAlimentar) throws SQLException{
        String insertPlanoAlimentarSql = "INSERT INTO plano_alimentar (descricao) VALUES (?)";
        PreparedStatement pstPlanoAlimentar = con.prepareStatement(insertPlanoAlimentarSql, Statement.RETURN_GENERATED_KEYS);
        pstPlanoAlimentar.setString(1, planoAlimentar.getDescricao());
        pstPlanoAlimentar.executeUpdate();
        
        //Retorna o id do plano alimentar
        ResultSet rs = pstPlanoAlimentar.getGeneratedKeys();
        int idPlanoAlimentar = 0;
        if (rs.next()) {
            idPlanoAlimentar = rs.getInt(1);
        }
        
        String insertRefeicoesSQL = "INSERT INTO plano_alimentar_refeicao (id_plano, id_refeicao, horario_refeicao) VALUES (?,?,?)";
        PreparedStatement pstRefeicao = con.prepareStatement(insertRefeicoesSQL);
        
        for (RefeicaoHorario rh : planoAlimentar.getRefeicoes()){
            pstRefeicao.setInt(1, idPlanoAlimentar);
            pstRefeicao.setDouble(2, rh.getIdRefeicao());
            pstRefeicao.setTime(3, rh.getHoraRefeicao());
            pstRefeicao.addBatch();
        }
        
        pstRefeicao.executeBatch();
        return true;
    }
    
    public static void listarPlanoAlimentarComDetalhes(Connection con) throws SQLException {
        String sql = """
            SELECT 
                pa.id_plano,
                pa.descricao AS plano_descricao,
                r.id_refeicao,
                r.nome_refeicao,
                r.descricao AS descricao_refeicao,
                pr.horario_refeicao,
                a.nome_alimento,
                ar.quantidade
            FROM plano_alimentar pa
            JOIN plano_alimentar_refeicao pr ON pa.id_plano = pr.id_plano
            JOIN refeicao r ON pr.id_refeicao = r.id_refeicao
            LEFT JOIN alimento_refeicao ar ON r.id_refeicao = ar.id_refeicao
            LEFT JOIN alimento a ON ar.id_alimento = a.id_alimento
            ORDER BY pa.id_plano, r.id_refeicao
        """;

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        int planoAtual = -1;
        int refeicaoAtual = -1;

        while (rs.next()) {
            int idPlano = rs.getInt("id_plano");
            int idRefeicao = rs.getInt("id_refeicao");

            // Mudança de plano
            if (idPlano != planoAtual) {
                planoAtual = idPlano;
                System.out.println("Plano ID: " + planoAtual);
                System.out.println("Descrição do Plano: " + rs.getString("plano_descricao"));
            }

            // Mudança de refeição
            if (idRefeicao != refeicaoAtual) {
                refeicaoAtual = idRefeicao;
                System.out.println("  Refeição ID: " + idRefeicao);
                System.out.println("  Nome da Refeição: " + rs.getString("nome_refeicao"));
                System.out.println("  Descrição: " + rs.getString("descricao_refeicao"));
                System.out.println("  Horário: " + rs.getTime("horario_refeicao"));
                System.out.println("  Alimentos:");
            }

            // Alimento pode ser nulo (LEFT JOIN)
            String nomeAlimento = rs.getString("nome_alimento");
            if (nomeAlimento != null) {
                System.out.printf("    - %s (quantidade: %.2f)%n", nomeAlimento, rs.getDouble("quantidade"));
            }
        }
    }
    
    
    // Método para listar planos alimentares com refeições e alimentos, filtrado pelo id do usuário
    public static void listarPlanoAlimentarPorUsuario(Connection con, int idUsuario) throws SQLException {
        // Consulta SQL para listar os planos alimentares associados ao usuário logado
        String sql = """
            SELECT 
                pa.id_plano,
                pa.descricao AS plano_descricao,
                r.id_refeicao,
                r.nome_refeicao,
                r.descricao AS descricao_refeicao,
                pr.horario_refeicao,
                a.nome_alimento,
                ar.quantidade
            FROM plano_alimentar pa
            JOIN plano_alimentar_usuario pua ON pa.id_plano = pua.id_plano
            JOIN plano_alimentar_refeicao pr ON pa.id_plano = pr.id_plano
            JOIN refeicao r ON pr.id_refeicao = r.id_refeicao
            LEFT JOIN alimento_refeicao ar ON r.id_refeicao = ar.id_refeicao
            LEFT JOIN alimento a ON ar.id_alimento = a.id_alimento
            WHERE pua.id_usuario = ?
            ORDER BY pa.id_plano, r.id_refeicao
        """;

        // Preparando a consulta com o id do usuário
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, idUsuario);  // Definindo o id do usuário logado na consulta
        ResultSet rs = pst.executeQuery();

        if (!rs.next()){
            System.out.println("Você não tem plano alimentar cadastrado, entre em cotato com o seu nutricionista");
            return;
        }
        
        int planoAtual = -1;
        int refeicaoAtual = -1;

        // Iterando os resultados
        while (rs.next()) {
            int idPlano = rs.getInt("id_plano");
            int idRefeicao = rs.getInt("id_refeicao");

            // Mudança de plano alimentar
            if (idPlano != planoAtual) {
                planoAtual = idPlano;
                System.out.println("Plano ID: " + planoAtual);
                System.out.println("Descrição do Plano: " + rs.getString("plano_descricao"));
            }

            // Mudança de refeição
            if (idRefeicao != refeicaoAtual) {
                refeicaoAtual = idRefeicao;
                System.out.println("  Refeição ID: " + idRefeicao);
                System.out.println("  Nome da Refeição: " + rs.getString("nome_refeicao"));
                System.out.println("  Descrição: " + rs.getString("descricao_refeicao"));
                System.out.println("  Horário: " + rs.getTime("horario_refeicao"));
                System.out.println("  Alimentos:");
            }

            // Verificar se há alimentos associados à refeição
            String nomeAlimento = rs.getString("nome_alimento");
            if (nomeAlimento != null) {
                System.out.printf("    - %s (quantidade: %.2f)%n", nomeAlimento, rs.getDouble("quantidade"));
            }
        }
    }
    
    public static boolean excluirPlanoAlimentar(Connection con, int idPlano) throws SQLException {
        // Verificar se o plano está vinculado a algum paciente
        String checkVinculoSql = "SELECT COUNT(*) FROM plano_alimentar_usuario WHERE id_plano = ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkVinculoSql)) {
            checkStmt.setInt(1, idPlano);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Existe vínculo com paciente — não pode excluir
            }
        }

        // Excluir vínculos do plano com refeições
        String deleteVinculosSql = "DELETE FROM plano_alimentar_refeicao WHERE id_plano = ?";
        try (PreparedStatement deleteVinculoStmt = con.prepareStatement(deleteVinculosSql)) {
            deleteVinculoStmt.setInt(1, idPlano);
            deleteVinculoStmt.executeUpdate();
        }

        // Excluir o plano alimentar
        String deletePlanoSql = "DELETE FROM plano_alimentar WHERE id_plano = ?";
        try (PreparedStatement deletePlanoStmt = con.prepareStatement(deletePlanoSql)) {
            deletePlanoStmt.setInt(1, idPlano);
            int rowsAffected = deletePlanoStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}

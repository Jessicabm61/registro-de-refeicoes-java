package model;
import been.UsuarioBeen;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class UsuarioModel {
    public static UsuarioBeen findUsuarioPorEmailSenha(Connection con, String email, String senha) throws SQLException {
        String sql = "SELECT id_usuario, nome, email, tipo_usuario FROM usuario WHERE email = ? AND senha = ?";
        
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, senha);
        
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            UsuarioBeen usuario = new UsuarioBeen();
            usuario.setId_usuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setTipo_usuario(rs.getString("tipo_usuario"));
            return usuario;
        } else {
            return null;
        }
    }
    
    public static int inserirPaciente(Connection con, UsuarioBeen paciente){
        String sql = "INSERT INTO usuario (nome, email, senha, data_nascimento, sexo, tipo_usuario) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEmail());
            stmt.setString(3, paciente.getSenha());
            stmt.setDate(4, (Date) paciente.getData_nascimento()); 
            stmt.setString(5, String.valueOf(paciente.getSexo())); 
            stmt.setString(6, paciente.getTipo_usuario());

            return stmt.executeUpdate(); // retorna 1 se inserção for bem-sucedida
        } catch (SQLException e) {
            System.err.println("Erro ao inserir paciente: " + e.getMessage());
            return -1;
        }
    }
    
    public static HashSet<UsuarioBeen> listAllPacientes(Connection con) throws SQLException{
        Statement st;
        HashSet <UsuarioBeen>list = new HashSet();
        st = con.createStatement();
        String sql = "SELECT id_usuario, nome, email, data_nascimento, sexo FROM usuario WHERE tipo_usuario = 'paciente'";
        ResultSet result = st.executeQuery(sql); //retorna uma lista e salva no Resultset result
        while(result.next()) {
            list.add(new UsuarioBeen(result.getInt(1),result.getString(2), result.getString(3), result.getDate(4), result.getString(5).charAt(0)));
        }
        return list;
    }
    
    public static boolean excluirPaciente(Connection con, int idPaciente) throws SQLException {
        // Excluir vínculos da tabela plano_alimentar_paciente
        String deleteVinculosSql = "DELETE FROM plano_alimentar_usuario WHERE id_usuario = ?";
        try (PreparedStatement stmtVinculo = con.prepareStatement(deleteVinculosSql)) {
            stmtVinculo.setInt(1, idPaciente);
            stmtVinculo.executeUpdate();
        }

        // Excluir o usuário (paciente)
        String deletePacienteSql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement stmtDelete = con.prepareStatement(deletePacienteSql)) {
            stmtDelete.setInt(1, idPaciente);
            int rowsAffected = stmtDelete.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public static UsuarioBeen buscarPacientePorId(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ? AND tipo_usuario = 'paciente'";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UsuarioBeen(
                    rs.getInt("id_usuario"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getDate("data_nascimento"),
                    rs.getString("sexo").charAt(0),
                    rs.getString("tipo_usuario")
                );
            }
            return null;
        }
    }

    public static boolean atualizarPaciente(Connection con, UsuarioBeen paciente) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, data_nascimento = ?, sexo = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEmail());
            stmt.setString(3, paciente.getSenha());
            stmt.setDate(4, new java.sql.Date(paciente.getData_nascimento().getTime()));
            stmt.setString(5, String.valueOf(paciente.getSexo()));
            stmt.setInt(6, paciente.getId_usuario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}

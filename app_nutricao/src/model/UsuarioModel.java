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
    public static String findTipoUsuarioEmailAndSenha(Connection con, String email, String senha) throws SQLException {
        String sql = "SELECT tipo_usuario FROM usuario WHERE email = ? and senha = ?";
     
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, senha);
        
        ResultSet result = pst.executeQuery();
        
        if (result.next()){
            return result.getString("tipo_usuario");
        }else {
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
}

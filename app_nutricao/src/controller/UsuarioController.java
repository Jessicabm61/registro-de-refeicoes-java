
package controller;

import been.UsuarioBeen;
import java.sql.SQLException;
import model.UsuarioModel;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class UsuarioController {
    
    public String login (Connection con, String email, String senha) {
        try {
            return UsuarioModel.findTipoUsuarioEmailAndSenha(con, email, senha);
        } catch (SQLException e){
            System.err.println("Erro ao tentar fazer login no banco de dados:");
            e.printStackTrace();
            return null;
        }
    }
    
    public static void listarPacientes(Connection con) throws SQLException{
        System.out.println("Lista de pacientes:");
        HashSet all = UsuarioModel.listAllPacientes(con);
        Iterator<UsuarioBeen> it = all.iterator();
        while(it.hasNext()){
            System.out.println(it.next().toString());
        }
    }
    
    public static int cadastrarPaciente(Connection con) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Dados do paciente");

        System.out.println("Nome do paciente: ");
        String nome = scan.nextLine();

        System.out.println("Email do paciente: ");
        String email = scan.nextLine();

        System.out.println("Senha de acesso: ");
        String senha = scan.nextLine();

        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        String dataStr = scan.nextLine();

        java.sql.Date dataNascimento = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dataUtil = sdf.parse(dataStr);
            dataNascimento = new java.sql.Date(dataUtil.getTime());
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
            return -1;
        }

        System.out.println("Sexo F/M/O: ");
        char sexo = scan.nextLine().toUpperCase().charAt(0);

        UsuarioBeen paciente = new UsuarioBeen(nome, email, senha, dataNascimento, sexo, "paciente");

        return UsuarioModel.inserirPaciente(con, paciente);
    }
}


package controller;

import been.UsuarioBeen;
import java.sql.SQLException;
import model.UsuarioModel;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Date;
import java.util.Scanner;
import model.PlanoPacienteModel;
import java.util.Scanner;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.SQLException;

public class UsuarioController {
    
    public static UsuarioBeen login(Connection con, String email, String senha) {
        try {
            return UsuarioModel.findUsuarioPorEmailSenha(con, email, senha);
        } catch (SQLException e) {
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

    public static void vincularPlanoAlimentar(Connection con) throws SQLException {
        Scanner scan = new Scanner(System.in);

        System.out.print("Digite o ID do plano alimentar: ");
        int idPlano = Integer.parseInt(scan.nextLine());

        System.out.print("Digite o ID do paciente (usuário): ");
        int idUsuario = Integer.parseInt(scan.nextLine());

        System.out.print("Digite a data de início (yyyy-mm-dd): ");
        String dataInicioStr = scan.nextLine();

        java.util.Date dataInicio = parseDate(dataInicioStr);

        System.out.print("Digite a data de fim (ou pressione ENTER para deixar em branco): ");
        String dataFimStr = scan.nextLine();
        java.util.Date dataFim = (dataFimStr.isEmpty()) ? null : parseDate(dataFimStr);

        java.sql.Date dataInicioSql = convertToSqlDate(dataInicio);
        java.sql.Date dataFimSql = (dataFim != null) ? convertToSqlDate(dataFim) : null;

        boolean sucesso = PlanoPacienteModel.vincularPlanoAoPaciente(con, idPlano, idUsuario, dataInicioSql, dataFimSql);

        if (sucesso) {
            System.out.println("Plano vinculado ao paciente com sucesso!");
        } else {
            System.out.println("Falha ao vincular plano ao paciente.");
        }
    }

    private static java.sql.Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateStr);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println("Formato de data inválido! Use o formato yyyy-MM-dd.");
            return null;
        }
    }

    private static java.sql.Date convertToSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }
    
    public static void excluirPaciente(Connection con) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Digite o ID do paciente que deseja excluir: ");
        int idPaciente = Integer.parseInt(scan.nextLine());

        try {
            boolean sucesso = UsuarioModel.excluirPaciente(con, idPaciente);
            if (sucesso) {
                System.out.println("Paciente excluído com sucesso.");
            } else {
                System.out.println("Erro: não foi possível excluir o paciente.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar excluir paciente:");
            e.printStackTrace();
        }
    }
    
   public static void editarPaciente(Connection con) {
    Scanner scan = new Scanner(System.in);

    System.out.print("Digite o ID do paciente que deseja editar: ");
    int id = Integer.parseInt(scan.nextLine());

    try {
        UsuarioBeen paciente = UsuarioModel.buscarPacientePorId(con, id);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.println("Pressione ENTER para manter o valor atual.");

        System.out.print("Nome atual: " + paciente.getNome() + " | Novo nome: ");
        String nome = scan.nextLine();
        if (!nome.isBlank()) paciente.setNome(nome);

        System.out.print("Email atual: " + paciente.getEmail() + " | Novo email: ");
        String email = scan.nextLine();
        if (!email.isBlank()) paciente.setEmail(email);

        System.out.print("Nova senha: ");
        String senha = scan.nextLine();
        if (!senha.isBlank()) paciente.setSenha(senha);

        System.out.print("Data de nascimento atual: " + paciente.getData_nascimento() + " | Nova (dd/MM/yyyy): ");
        String dataStr = scan.nextLine();
        if (!dataStr.isBlank()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date dataUtil = sdf.parse(dataStr);
                paciente.setData_nascimento(new java.sql.Date(dataUtil.getTime()));
            } catch (ParseException e) {
                System.out.println("Data inválida, mantendo valor atual.");
            }
        }

        System.out.print("Sexo atual: " + paciente.getSexo() + " | Novo sexo (F/M/O): ");
        String sexoStr = scan.nextLine();
        if (!sexoStr.isBlank()) paciente.setSexo(sexoStr.toUpperCase().charAt(0));

        boolean sucesso = UsuarioModel.atualizarPaciente(con, paciente);
        if (sucesso) {
            System.out.println("Paciente atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar paciente.");
        }

    } catch (Exception e) {
        System.err.println("Erro ao editar paciente:");
        e.printStackTrace();
    }
}
}


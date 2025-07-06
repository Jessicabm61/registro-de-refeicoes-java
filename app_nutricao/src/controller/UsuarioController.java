package controller;

import been.UsuarioBeen;
import model.UsuarioModel;
import model.PlanoPacienteModel;
import org.neo4j.driver.Driver;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

public class UsuarioController {

    public static UsuarioBeen login(Driver driver, String email, String senha) {
        try {
            return UsuarioModel.findUsuarioPorEmailSenha(driver, email, senha);
        } catch (Exception e) {
            System.err.println("Erro ao tentar fazer login no banco de dados:");
            e.printStackTrace();
            return null;
        }
    }

    public static void listarPacientes(Driver driver) {
        System.out.println("\n======================");
        System.out.println("  LISTANDO PACIENTES  ");
        System.out.println("========================\n");
        HashSet<UsuarioBeen> all = UsuarioModel.listAllPacientes(driver);
        for (UsuarioBeen u : all) {
            System.out.println(u);
        }
    }

    public static void cadastrarUsuario(Driver driver, String tipoUsuario) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n======================");
        System.out.println("  CADASTRO DE USUARIOS  ");
        System.out.println("========================\n");

        System.out.print("Nome do usuário: ");
        String nome = scan.nextLine();

        System.out.print("Email do usuário: ");
        String email = scan.nextLine();

        System.out.print("Senha de acesso: ");
        String senha = scan.nextLine();

        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        String dataStr = scan.nextLine();

        Date dataUtil;
        try {
            dataUtil = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
            return;
        }
        java.sql.Date dataNascimento = new java.sql.Date(dataUtil.getTime());

        System.out.print("Sexo F/M/O: ");
        char sexo = scan.nextLine().toUpperCase().charAt(0);

        UsuarioBeen usuario = new UsuarioBeen(nome, email, senha, dataNascimento, sexo, tipoUsuario);

        boolean sucesso = UsuarioModel.inserirUsuario(driver, usuario);
        System.out.println(sucesso ? "Usuário cadastrado com sucesso!" : "Falha ao cadastrar usuário.");
    }

    public static void vincularPlanoAlimentar(Driver driver) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n============================");
        System.out.println("  VINCULANDO PLANO ALIMENTAR  ");
        System.out.println("============================\n");

        System.out.print("Digite o nome do plano alimentar: ");
        String nomePlano = scan.nextLine();
        
        System.out.print("Digite o nome do paciente (usuário): ");
        String nomeUsuario = scan.nextLine();
        
        System.out.print("Digite a data de início (yyyy-MM-dd): ");
        java.sql.Date dataInicio = parseSqlDate(scan.nextLine());

        System.out.print("Digite a data de fim (ou ENTER para sem data): ");
        String fimStr = scan.nextLine();
        java.sql.Date dataFim = fimStr.isBlank() ? null : parseSqlDate(fimStr);

        boolean sucesso = PlanoPacienteModel.vincularPlanoAoPaciente(driver, nomePlano, nomeUsuario, dataInicio, dataFim);
        System.out.println(sucesso ? "Plano vinculado com sucesso!" : "Falha ao vincular plano.");
    }

    public static void excluirPaciente(Driver driver) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n============================");
        System.out.println("  EXCLUINDO PACIENTE ");
        System.out.println("=============================\n");
        System.out.print("Digite o nome do paciente que deseja excluir: ");
        String nomePaciente = scan.nextLine();
        
        boolean sucesso = UsuarioModel.excluirPaciente(driver, nomePaciente);
        System.out.println(sucesso ? "Paciente excluído com sucesso." : "Erro ao excluir paciente.");
    }

    public static void editarPaciente(Driver driver) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n============================");
        System.out.println("  EDITANDO PACIENTE ");
        System.out.println("=============================\n");
        System.out.print("Digite o nome do paciente que deseja editar: ");
        String nomePaciente = scan.nextLine();
        
        UsuarioBeen paciente = UsuarioModel.buscarPacientePorNome(driver, nomePaciente);
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
                java.util.Date parsed = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
                paciente.setData_nascimento(new java.sql.Date(parsed.getTime()));
            } catch (ParseException e) {
                System.out.println("Data inválida, mantendo valor atual.");
            }
        }

        System.out.print("Sexo atual: " + paciente.getSexo() + " | Novo sexo (F/M/O): ");
        String sexoStr = scan.nextLine();
        if (!sexoStr.isBlank()) paciente.setSexo(sexoStr.toUpperCase().charAt(0));

        boolean sucesso = UsuarioModel.atualizarPaciente(driver, paciente, nomePaciente);
        System.out.println(sucesso ? "Paciente atualizado com sucesso!" : "Erro ao atualizar paciente.");
    }

    private static java.sql.Date parseSqlDate(String dateStr) {
        try {
            java.util.Date util = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            return new java.sql.Date(util.getTime());
        } catch (Exception e) {
            System.out.println("Formato inválido (" + dateStr + "). Use yyyy-MM-dd.");
            return null;
        }
    }
}

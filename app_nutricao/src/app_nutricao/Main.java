
package app_nutricao;

import config.Conexao;
import controller.AlimentoController;
import controller.UsuarioController;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import controller.RefeicaoController;
import java.util.Scanner;
import controller.AlimentoController;


public class Main {

    public static void main(String[] args) throws SQLException {
        Conexao c = new Conexao();
        Connection con = c.getConnection();
        
        Scanner scanner = new Scanner(System.in);
        UsuarioController usuarioController = new UsuarioController();
        String tipoUsuario = "";
        int opcao;
        do {
            System.out.println("Bem vindo ao Sistema de nutrição");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Login:");
            System.out.println("2 - Cadastro:");
        
            opcao = scanner.nextInt();
            scanner.nextLine();
        
            if (opcao == 1) {
                System.out.println("E-mail:");
                String email = scanner.nextLine();

                System.out.println("senha:");
                String senha = scanner.nextLine();

                tipoUsuario = usuarioController.login(con, email, senha); //retorna o tipo de usuario

                if (tipoUsuario != null){
                    System.out.println("Login bem-sucedido");
                    exibirMenuPorTipo(tipoUsuario, con);
                } else {
                    System.out.println("Email e senha incorreto");
                }
            }
        } while(tipoUsuario == null || (opcao != 1 && opcao != 2));
    }
    
    public static void exibirMenuPorTipo(String tipoUsuario, Connection con) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        switch (tipoUsuario) {
        case "nutricionista":
            
            int opcaoNutricionista;
            do{
                System.out.println("Menu Nutricionista");
                System.out.println("1 - Listar alimentos");
                System.out.println("2 - Cadastrar refeições");
                System.out.println("3 - Listar refeições");
                System.out.println("4 - Cadastrar paciente");
                System.out.println("5 - Listar pacientes");
                System.out.println("5 - Cadastrar plano alimentar");
                System.out.println("6 - Vincular plano alimentar a paciente");
                System.out.println("7 - Excluir....");
                System.out.println("8 - Sair");

                
                System.out.print("Escolha uma opção: ");
                opcaoNutricionista = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha

                switch (opcaoNutricionista) {
                case 1:
                    AlimentoController.listarAlimentos(con); 
                    break;
                case 2:
                    RefeicaoController.createRefeicao(con);
                    break;
                case 3:
                    RefeicaoController.listarRefeicoes(con);
                    break;
                case 4: 
                    UsuarioController.cadastrarPaciente(con);
                    break;
                case 5:
                    UsuarioController.listarPacientes(con);
                    break;
                case 6:
                    System.out.println("Listando plano alimentar...");
                    // Lógica para listar plano alimentar
                    break;
                case 7:
                    System.out.println("Vinculando plano alimentar a paciente...");
                    // Lógica para vincular plano alimentar a paciente
                    break;
                case 8:
                    System.out.println("Cadastrando paciente...");
                    // Lógica para cadastrar paciente
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
                }
                
            }while(opcaoNutricionista != 8);
            break;
            
        case "paciente":
            System.out.println("Menu Paciente");
            // Lógica para o menu do paciente
            break;

        case "admin":
            System.out.println("Menu Admin");
            // Lógica para o menu do admin
            break;

        default:
            System.out.println("Opção de usuário inválida");
            
        }
    }
}

    

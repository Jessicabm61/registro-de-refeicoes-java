
package app_nutricao;

import been.UsuarioBeen;
import config.Conexao;
import controller.AlimentoController;
import controller.UsuarioController;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import controller.RefeicaoController;
import java.util.Scanner;
import controller.AlimentoController;
import controller.PlanoAlimentarController;


public class Main {

    public static void main(String[] args) throws SQLException {
        Conexao c = new Conexao();
        Connection con = c.getConnection();
        
        Scanner scanner = new Scanner(System.in);
        UsuarioController usuarioController = new UsuarioController();
        UsuarioBeen usuarioLogado = null;
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

                usuarioLogado = usuarioController.login(con, email, senha);
                
                 if (usuarioLogado != null) {
                    tipoUsuario = usuarioLogado.getTipo_usuario(); // Obtém o tipo do usuário
                    System.out.println("Login bem-sucedido");
                    exibirMenuPorTipo(tipoUsuario, con, usuarioLogado); // Passa o usuário logado para o menu
                } else {
                    System.out.println("E-mail e senha incorretos");
                }
            }
        } while(tipoUsuario == null || (opcao != 1 && opcao != 2));
    }
    
    public static void exibirMenuPorTipo(String tipoUsuario, Connection con, UsuarioBeen usuarioLogado) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        switch (tipoUsuario) {
        case "nutricionista":
            
            int opcaoNutricionista;
            do{
                System.out.println(" ");
                System.out.println("\n=====================");
                System.out.println("  MENU NUTRICIONISTA  ");
                System.out.println("=====================\n");

                System.out.println(" ");
                System.out.println("REFEICOES");
                System.out.println("1 - Cadastrar refeições");
                System.out.println("2 - Excluir refeição");
                
                System.out.println(" ");
                System.out.println("PLANO ALIEMNTAR");
                System.out.println("3 - Cadastrar plano alimentar");
                System.out.println("4 - Excluir plano alimentar");
                
                System.out.println(" ");
                System.out.println("PACIENTES");
                System.out.println("5 - Cadastrar paciente");
                System.out.println("6 - Vincular plano alimentar a paciente");
                System.out.println("7 - Editar paciente");
                System.out.println("8 - Excluir paciente");
                
                System.out.println(" ");
                System.out.println("RELATORIOS");
                System.out.println("9 - Listar alimentos");
                System.out.println("10 - Listar refeições");
                System.out.println("11 - Listar pacientes");
                System.out.println("12 - Listar plano alimentar");

                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                opcaoNutricionista = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha

                switch (opcaoNutricionista) {
                case 1:
                    RefeicaoController.createRefeicao(con);
                    break;
                case 2:
                    RefeicaoController.excluirRefeicao(con);
                    break;
                case 3:
                    PlanoAlimentarController.createPlanoAlimentar(con);
                    break;
                case 4:
                    PlanoAlimentarController.excluirPlanoAlimentar(con);
                    break;
                case 5:
                    UsuarioController.cadastrarPaciente(con);
                    break;
                case 6:
                    UsuarioController.vincularPlanoAlimentar(con);
                    break;
                case 7:
                    UsuarioController.editarPaciente(con);
                    break;
                case 8:
                    UsuarioController.excluirPaciente(con);
                    break;
                case 9:
                    AlimentoController.listarAlimentos(con); 
                case 10:
                    RefeicaoController.listarRefeicoes(con);
                break;
                case 11:
                    UsuarioController.listarPacientes(con);
                break;    
                case 12:
                    PlanoAlimentarController.listarPlanoAlimentar(con);
                default:
                    System.out.println("Opção inválida");
                    break;
                }
                
            }while(opcaoNutricionista != 0);
            break;
            
        case "paciente":
            System.out.println(" ");
            System.out.println("\n=====================");
            System.out.println("  MENU PACIENTE  ");
            System.out.println("=====================\n");
            System.out.println("1 - Listar plano alimentar");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            
            String opcaoPaciente = scanner.nextLine();
            
            switch (opcaoPaciente) {
            case "1":
                // Chamar função para listar plano alimentar vinculado ao paciente
                System.out.println("Listou?");
                PlanoAlimentarController.listarPlanoAlimentarPorUsuario(con, usuarioLogado.getId_usuario());
                break;
            case "0":
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
            }
        break;
        default:
            System.out.println("Opção de usuário inválida");
        }
    }
}

    

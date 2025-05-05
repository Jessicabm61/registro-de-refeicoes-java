package controller;
import been.PlanoAlimentarBean;
import java.sql.Connection; 
import java.util.Scanner;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.PlanoAlimentarModel;
import model.RefeicaoHorario;
import java.sql.SQLException;

public class PlanoAlimentarController {
    public static void createPlanoAlimentar(Connection con){
        
        Scanner scan = new Scanner(System.in);
        
        try{
            System.out.println("Cadastrando plano alimentar...");
            System.out.println("Descrição do plano alimentar:");
            String descricao = scan.next();
            PlanoAlimentarBean planoAlimentar = new PlanoAlimentarBean(descricao);
            
            //Monta a lista de refeicoes
            List<RefeicaoHorario> refeicoes = new ArrayList();
            String continuar;
            
            do {
                System.out.println("Vinculando refeições ao plano aliemntar");
                
                System.out.println("Digite um código de refeição");
                int codigoRefeicao = scan.nextInt();
                scan.nextLine();
           
                //Seleciona um horário para refeicao
                System.out.println("Digite um horário para a refeição (HH:mm:ss):");
                String horaStr = scan.nextLine();

                Time horario = null;
                try {
                    horario = Time.valueOf(horaStr); // Converte String para java.sql.Time
                    System.out.println("Horário convertido: " + horario);
                } catch (IllegalArgumentException e) {
                    System.out.println("Formato inválido! Use HH:mm:ss");
                }
                
                //Cria uma Refeicao com Horario e seta na lista do plano alimentar
                RefeicaoHorario refeicaoHorario = new RefeicaoHorario(codigoRefeicao, horario);
                refeicoes.add(refeicaoHorario);
                
                System.out.println("Deseja adicionar outra refeicao? (s/n)");
                continuar = scan.nextLine();
            }while (continuar.equalsIgnoreCase("s"));
            
            //Seta a lista de refeicoes no plano alimentar
            planoAlimentar.setRefeicao(refeicoes);
            
            //Chama a Model para inserir no Banco de dados
            if (PlanoAlimentarModel.inserirPlanoAlimentar(con, planoAlimentar)) {
                System.out.println("Refeição cadastrada com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar refeição.");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public static void listarPlanoAlimentar(Connection con) throws SQLException {
        PlanoAlimentarModel.listarPlanoAlimentarComDetalhes(con);
    }
     
    // Método para listar os planos alimentares com detalhes, filtrado pelo id do usuário
    public static void listarPlanoAlimentarPorUsuario(Connection con, int idUsuario) throws SQLException {
        PlanoAlimentarModel.listarPlanoAlimentarPorUsuario(con, idUsuario);
    }
    
    public static void excluirPlanoAlimentar(Connection con) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Digite o ID do plano alimentar que deseja excluir: ");
        int idPlano = Integer.parseInt(scan.nextLine());

        try {
            boolean sucesso = PlanoAlimentarModel.excluirPlanoAlimentar(con, idPlano);
            if (sucesso) {
                System.out.println("Plano alimentar excluído com sucesso.");
            } else {
                System.out.println("Não foi possível excluir o plano. Ele está vinculado a um paciente.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar excluir o plano alimentar:");
            e.printStackTrace();
        }
    }
}

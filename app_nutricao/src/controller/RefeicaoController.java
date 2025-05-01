
package controller;

import been.RefeicaoBean;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.RefeicaoModel;
import been.AlimentoBeen;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import model.AlimentoQuantidade;

public class RefeicaoController {
    public static void createRefeicao(Connection con){
        
        Scanner scan = new Scanner(System.in);
        
        //Coleta os dados para cadastrar a refeição
        try{
            System.out.println("Cadastrando refeições...");
            System.out.println("Nome da refeição:");
            String nome = scan.next();
            
            System.out.println("Descrição da refeição:");
            String descricao = scan.next();
            
            //Cria uma refeição e seta nome e descrição
            RefeicaoBean refeicao = new RefeicaoBean();
            refeicao.setNome(nome);
            refeicao.setDescricao(descricao);
            
            //Monta a lista de alimentos
            List<AlimentoQuantidade> alimentos = new ArrayList<>();
            
            String continuar;
            
            do {
                System.out.println("ID do alimento:");
                int idAlimento = scan.nextInt();
                scan.nextLine();
                System.out.println("Quantidade (g):");
                double quantidade = scan.nextDouble();
                scan.nextLine();
                
                //Cria um alimento e seta na lista de AlimentoQuantidade
                AlimentoBeen alimento = new AlimentoBeen();
                alimento.setIdAlimento(idAlimento);
                alimentos.add(new AlimentoQuantidade(alimento, quantidade));
                
                System.out.println("Deseja adicionar outro alimento? (s/n)");
                continuar = scan.nextLine();
            } while (continuar.equalsIgnoreCase("s"));
            
            //seta o alimento a lista de refeicao
            refeicao.setAlimentos(alimentos);
            
            //Chama a Model para inserir no Banco de dados
            if (RefeicaoModel.inserirRefeicaoComAlimentos(con, refeicao)) {
                System.out.println("Refeição cadastrada com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar refeição.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void listarRefeicoes(Connection con) throws SQLException {
        RefeicaoModel.listarRefeicoesComDetalhes(con);
    }
}


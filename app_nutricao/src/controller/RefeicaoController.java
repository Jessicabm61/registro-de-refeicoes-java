package controller;

import been.RefeicaoBean;
import model.RefeicaoModel;
import been.AlimentoBeen;
import model.AlimentoQuantidade;
import org.neo4j.driver.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RefeicaoController {

    public static void createRefeicao(Driver driver) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("\n======================");
            System.out.println("  CADASTRO DE REFEICAO  ");
            System.out.println("======================\n");
            System.out.println("Nome da refeição:");
            String nome = scan.nextLine();

            System.out.println("Descrição da refeição:");
            String descricao = scan.nextLine();

            RefeicaoBean refeicao = new RefeicaoBean();
            refeicao.setNome(nome);
            refeicao.setDescricao(descricao);

            List<AlimentoQuantidade> alimentos = new ArrayList<>();

            String continuar;
            do {
                System.out.println("ID do alimento:");
                int idAlimento = Integer.parseInt(scan.nextLine());

                System.out.println("Quantidade (g):");
                double quantidade = Double.parseDouble(scan.nextLine());

                AlimentoBeen alimento = new AlimentoBeen();
                alimento.setIdAlimento(idAlimento);

                alimentos.add(new AlimentoQuantidade(alimento, quantidade));

                System.out.println("Deseja adicionar outro alimento? (s/n)");
                continuar = scan.nextLine();
            } while (continuar.equalsIgnoreCase("s"));

            refeicao.setAlimentos(alimentos);

            if (RefeicaoModel.inserirRefeicaoComAlimentos(driver, refeicao)) {
                System.out.println("Refeição cadastrada com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar refeição.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listarRefeicoes(Driver driver) {
        System.out.println("\n======================");
        System.out.println("  LISTA DE REFEICOES  ");
        System.out.println("=======================\n");

        try {
            RefeicaoModel.listarRefeicoesComDetalhes(driver);
        } catch (Exception e) {
            System.err.println("Erro ao listar refeições:");
            e.printStackTrace();
        }
    }

    public static void excluirRefeicao(Driver driver) {
        System.out.println("\n======================");
        System.out.println("  EXCLUINDO REFEICAO  ");
        System.out.println("=======================\n");

        Scanner scan = new Scanner(System.in);
        System.out.print("Digite o nome da refeição que deseja excluir: ");
        String nomeRefeicao = scan.nextLine();

        try {
            boolean sucesso = RefeicaoModel.excluirRefeicao(driver, nomeRefeicao);
            if (sucesso) {
                System.out.println("Refeição excluída com sucesso.");
            } else {
                System.out.println("Não foi possível excluir a refeição. Ela pode estar vinculada a algum plano alimentar.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir a refeição:");
            e.printStackTrace();
        }
    }
}

package controller;

import been.PlanoAlimentarBean;
import java.util.Scanner;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.PlanoAlimentarModel;
import model.RefeicaoHorario;
import org.neo4j.driver.Driver;

public class PlanoAlimentarController {

    public static void createPlanoAlimentar(Driver driver) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("\n============================");
            System.out.println("  CADASTRANDO PLANO ALIMENTAR ");
            System.out.println("=============================\n");
            System.out.println("Descrição do plano alimentar:");
            String descricao = scan.nextLine();

            PlanoAlimentarBean planoAlimentar = new PlanoAlimentarBean(descricao);

            // Monta a lista de refeições
            List<RefeicaoHorario> refeicoes = new ArrayList<>();
            String continuar;

            do {
                System.out.println("Vinculando refeições ao plano alimentar");

                System.out.println("Digite o nome da refeição refeição");
                String nomeRefeicao = scan.nextLine();

                System.out.println("Digite um horário para a refeição (HH:mm:ss):");
                String horaStr = scan.nextLine();

                Time horario = null;
                try {
                    horario = Time.valueOf(horaStr); // Converte String para java.sql.Time
                    System.out.println("Horário convertido: " + horario);
                } catch (IllegalArgumentException e) {
                    System.out.println("Formato inválido! Use HH:mm:ss");
                }

                RefeicaoHorario refeicaoHorario = new RefeicaoHorario(nomeRefeicao, horario);
                refeicoes.add(refeicaoHorario);

                System.out.println("Deseja adicionar outra refeição? (s/n)");
                continuar = scan.nextLine();
            } while (continuar.equalsIgnoreCase("s"));

            planoAlimentar.setRefeicao(refeicoes);

            if (PlanoAlimentarModel.inserirPlanoAlimentar(driver, planoAlimentar)) {
                System.out.println("Plano alimentar cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar plano alimentar.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listarPlanoAlimentar(Driver driver) {
        System.out.println("\n============================");
        System.out.println("  LISTANDO PLANO ALIMENTAR ");
        System.out.println("=============================\n");

        try {
            PlanoAlimentarModel.listarPlanoAlimentarComDetalhes(driver);
        } catch (Exception e) {
            System.err.println("Erro ao listar planos alimentares:");
            e.printStackTrace();
        }
    }

    public static void listarPlanoAlimentarPorUsuario(Driver driver, String nomeUsuario) {
        System.out.println("\n====================================");
        System.out.println("  LISTANDO PLANO ALIMENTAR DO USUARIO ");
        System.out.println("====================================\n");

        try {
            PlanoAlimentarModel.listarPlanoAlimentarPorUsuario(driver, nomeUsuario);
        } catch (Exception e) {
            System.err.println("Erro ao listar planos alimentares do usuário:");
            e.printStackTrace();
        }
    }

    public static void excluirPlanoAlimentar(Driver driver) {
        System.out.println("\n====================================");
        System.out.println("  EXCLUINDO PLANO ALIMENTAR ");
        System.out.println("====================================\n");

        Scanner scan = new Scanner(System.in);
        System.out.print("Digite o nome do plano alimentar que deseja excluir: ");
        String nomePlano = scan.nextLine();
        
        try {
            boolean sucesso = PlanoAlimentarModel.excluirPlanoAlimentar(driver, nomePlano);
            if (sucesso) {
                System.out.println("Plano alimentar excluído com sucesso.");
            } else {
                System.out.println("Não foi possível excluir o plano. Ele está vinculado a um paciente.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao tentar excluir o plano alimentar:");
            e.printStackTrace();
        }
    }
}

package controller;

import been.AlimentoBeen;
import java.util.HashSet;
import java.util.Iterator;
import model.AlimentoModel;
import org.neo4j.driver.Driver;

public class AlimentoController {
    public static void listarAlimentos(Driver driver) {
        System.out.println("\n====================================");
        System.out.println("  LISTANDO ALIMENTOS ");
        System.out.println("====================================\n");
        System.out.println("Lista de Alimentos:");

        try {
            HashSet<AlimentoBeen> all = AlimentoModel.listAll(driver);
            Iterator<AlimentoBeen> it = all.iterator();
            while (it.hasNext()) {
                System.out.println(it.next().toString());
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar alimentos:");
            e.printStackTrace();
        }
    }
}

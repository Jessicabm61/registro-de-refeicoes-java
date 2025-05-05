
package controller;
import been.AlimentoBeen;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import model.AlimentoModel;

public class AlimentoController {
    public static void listarAlimentos(Connection con) throws SQLException{
        System.out.println("\n====================================");
        System.out.println("  LISTANDO ALIMENTOS ");
        System.out.println("====================================\n");
        System.out.println("Lista de Alimentos:");
        HashSet all = AlimentoModel.listAll(con);
        Iterator<AlimentoBeen> it = all.iterator();
        while(it.hasNext()){
            System.out.println(it.next().toString());
        }
    }
}

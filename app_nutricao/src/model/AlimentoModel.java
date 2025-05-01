
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import been.AlimentoBeen;

public class AlimentoModel {

    public static HashSet listAll(Connection con) throws SQLException{
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        String sql = "SELECT id_alimento, nome_alimento, calorias, proteinas, carboidratos FROM alimento";
        ResultSet result = st.executeQuery(sql); //retorna uma lista e salva no Resultset result
        while(result.next()) {
            list.add(new AlimentoBeen(result.getInt(1),result.getString(2), result.getDouble(3), result.getDouble(4),
            		result.getInt(5))); //para cada elemento da lista cria um been do modelo (em memoria) e passa os parametros pelo construtor
        }
        return list;
    }
}


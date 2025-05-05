package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlanoPacienteModel {
     public static boolean vincularPlanoAoPaciente(Connection con, int idPlano, int idUsuario, Date dataInicio, Date dataFim) throws SQLException {
        String sql = """
            INSERT INTO plano_alimentar_usuario (id_plano, id_usuario, data_inicio, data_fim)
            VALUES (?, ?, ?, ?)
        """;

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, idPlano);
        pst.setInt(2, idUsuario);
        pst.setDate(3, dataInicio);

        if (dataFim != null) {
            pst.setDate(4, dataFim);
        } else {
            pst.setNull(4, java.sql.Types.DATE);
        }

        int linhasAfetadas = pst.executeUpdate();
        return linhasAfetadas > 0;
    }
    
}

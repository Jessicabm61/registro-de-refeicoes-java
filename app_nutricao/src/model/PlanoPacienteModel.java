package model;

import org.neo4j.driver.*;
import java.sql.Date;

public class PlanoPacienteModel {

    public static boolean vincularPlanoAoPaciente(Driver driver, String nomePlano, String nomeUsuario, Date dataInicio, Date dataFim) {
        try (Session session = driver.session()) {
            return session.writeTransaction(tx -> {

                String query = """
                    MATCH (u:Usuario {nome: $nomeUsuario}), (p:PlanoAlimentar {descricao: $nomePlano})
                    MERGE (u)-[r:TEM_PLANO]->(p)
                    SET r.data_inicio = $dataInicio,
                        r.data_fim = $dataFim
                """;

                tx.run(query, Values.parameters(
                        "nomeUsuario", nomeUsuario,
                        "nomePlano", nomePlano,
                        "dataInicio", dataInicio.toString(),
                        "dataFim", (dataFim != null) ? dataFim.toString() : null
                ));

                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

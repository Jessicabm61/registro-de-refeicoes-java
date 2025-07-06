package model;

import been.AlimentoBeen;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import java.util.HashSet;
import org.neo4j.driver.Driver;

public class AlimentoModel {

   public static HashSet<AlimentoBeen> listAll(Driver driver) {
    HashSet<AlimentoBeen> list = new HashSet<>();

    String cypher = "MATCH (a:alimento) RETURN a.id_alimento AS id, a.nome_alimento AS nome, " +
                    "a.calorias AS calorias, a.proteinas AS proteinas, a.carboidratos AS carboidratos";

    try (Session session = driver.session()) {
        Result result = session.run(cypher);

        while (result.hasNext()) {
            var record = result.next();

            int id = record.get("id").asInt();
            String nome = record.get("nome").asString();
            double calorias = record.get("calorias").asDouble();
            double proteinas = record.get("proteinas").asDouble();
            double carboidratosDouble = record.get("carboidratos").asDouble();
            int carboidratos = (int) Math.round(carboidratosDouble);


            list.add(new AlimentoBeen(id, nome, calorias, proteinas, carboidratos));
        }
    }

    return list;
}
}

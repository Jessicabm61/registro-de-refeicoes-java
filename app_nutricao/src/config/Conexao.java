package config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class Conexao {

    private final Driver driver;

    public Conexao() {
        String uri = "bolt://localhost:7687";
        String user = "neo4j";
        String password = "adminadmin";

        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public Session getSession() {
        return driver.session();
    }

    public Driver getDriver() {
        return driver;
    }
    
    public void close() {
        driver.close();
    }
}

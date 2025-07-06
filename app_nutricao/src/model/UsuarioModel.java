package model;

import been.UsuarioBeen;
import org.neo4j.driver.*;
import java.sql.Date;
import java.util.HashSet;

public class UsuarioModel {

    // Buscar usuário por e-mail e senha
    public static UsuarioBeen findUsuarioPorEmailSenha(Driver driver, String email, String senha) {
        try (Session session = driver.session()) {
            return session.readTransaction(tx -> {
                String query = """
                    MATCH (u:Usuario {email: $email, senha: $senha})
                    RETURN u
                """;
                Result result = tx.run(query, Values.parameters("email", email, "senha", senha));
                if (result.hasNext()) {
                    var u = result.single().get("u").asNode();
                    return new UsuarioBeen(
                        u.get("nome").asString(),
                        u.get("email").asString(),
                        u.get("senha").asString(),
                        Date.valueOf(u.get("data_nascimento").asString()),
                        u.get("sexo").asString().charAt(0),
                        u.get("tipo_usuario").asString()
                    );
                } else {
                    return null;
                }
            });
        }
    }

    // Inserir novo usuário
    public static boolean inserirUsuario(Driver driver, UsuarioBeen usuario) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                String query = """
                    CREATE (u:Usuario {
                        nome: $nome,
                        email: $email,
                        senha: $senha,
                        data_nascimento: $data_nascimento,
                        sexo: $sexo,
                        tipo_usuario: $tipo_usuario
                    })
                """;
                tx.run(query, Values.parameters(
                    "nome", usuario.getNome(),
                    "email", usuario.getEmail(),
                    "senha", usuario.getSenha(),
                    "data_nascimento", usuario.getData_nascimento().toString(),
                    "sexo", String.valueOf(usuario.getSexo()),
                    "tipo_usuario", usuario.getTipo_usuario()
                ));
                return null;
            });
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }

    // Listar todos os pacientes
    public static HashSet<UsuarioBeen> listAllPacientes(Driver driver) {
        HashSet<UsuarioBeen> list = new HashSet<>();
        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                String query = """
                    MATCH (u:Usuario {tipo_usuario: 'paciente'})
                    RETURN u
                """;
                Result rs = tx.run(query);
                while (rs.hasNext()) {
                    var u = rs.next().get("u").asNode();
                    list.add(new UsuarioBeen(
                        u.get("nome").asString(),
                        u.get("email").asString(),
                        Date.valueOf(u.get("data_nascimento").asString()),
                        u.get("sexo").asString().charAt(0)
                    ));
                }
                return null;
            });
        } catch (Exception e) {
            System.err.println("Erro ao listar pacientes: " + e.getMessage());
        }
        return list;
    }

    // Buscar paciente por ID
    public static UsuarioBeen buscarPacientePorNome(Driver driver, String nomePaciente) {
        try (Session session = driver.session()) {
            return session.readTransaction(tx -> {
                String query = """
                    MATCH (u:Usuario {nome: $nomePaciente, tipo_usuario: 'paciente'})
                    RETURN u
                """;
                Result rs = tx.run(query, Values.parameters("nomePaciente", nomePaciente));
                if (rs.hasNext()) {
                    var u = rs.single().get("u").asNode();
                    return new UsuarioBeen(
                        u.get("nome").asString(),
                        u.get("email").asString(),
                        u.get("senha").asString(),
                        Date.valueOf(u.get("data_nascimento").asString()),
                        u.get("sexo").asString().charAt(0),
                        u.get("tipo_usuario").asString()
                    );
                }
                return null;
            });
        }
    }

    // Atualizar paciente
    public static boolean atualizarPaciente(Driver driver, UsuarioBeen paciente, String nomePaciente) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                String query = """
                    MATCH (u:Usuario {nome: $nomePaciente})
                    SET u.nome = $nome,
                        u.email = $email,
                        u.senha = $senha,
                        u.data_nascimento = $data_nascimento,
                        u.sexo = $sexo
                """;
                tx.run(query, Values.parameters(
                    "nomePaciente", nomePaciente,
                    "nome", paciente.getNome(),
                    "email", paciente.getEmail(),
                    "senha", paciente.getSenha(),
                    "data_nascimento", paciente.getData_nascimento().toString(),
                    "sexo", String.valueOf(paciente.getSexo())
                ));
                return null;
            });
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar paciente: " + e.getMessage());
            return false;
        }
    }

    // Excluir paciente
    public static boolean excluirPaciente(Driver driver, String nomePaciente) {
        try (Session session = driver.session()) {
            return session.writeTransaction(tx -> {
                // Deleta vínculo com plano (relacionamento)
                String deleteRel = """
                    MATCH (u:Usuario {nome: $nome})-[r:TEM_PLANO]->()
                    DELETE r
                """;
                tx.run(deleteRel, Values.parameters("nome", nomePaciente));

                // Deleta o nó do usuário
                String deleteNode = """
                    MATCH (u:Usuario {nome: $nome})
                    DELETE u
                """;
                tx.run(deleteNode, Values.parameters("nome", nomePaciente));

                return true;
            });
        } catch (Exception e) {
            System.err.println("Erro ao excluir paciente: " + e.getMessage());
            return false;
        }
    }
}

package been;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuarioBeen {
    
    private int id_usuario;
    private String nome;
    private String email;
    private String senha;
    private Date data_nascimento;
    private char sexo; 
    private String tipo_usuario;
    
    // Construtor
    public UsuarioBeen(int id_usuario, String nome, String email, String senha, Date data_nascimento, char sexo, String tipo_usuario) {
        this.id_usuario = id_usuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.tipo_usuario = tipo_usuario;
    }
    
    public UsuarioBeen(String nome, String email, String senha, Date data_nascimento, char sexo, String tipo_usuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.tipo_usuario = tipo_usuario;
    }
    
     public UsuarioBeen(int id_usuario, String nome, String email, Date data_nascimento, char sexo) {
        this.id_usuario = id_usuario;
         this.nome = nome;
        this.email = email;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
    }
    
    // Getters e Setters
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %-5d | Nome: %-20s | E-mail: %-30s | Data Nascimento: %-12s | Sexo: %-2s",
            id_usuario, nome, email, new SimpleDateFormat("dd/MM/yyyy").format(data_nascimento), sexo
        );
    }
}

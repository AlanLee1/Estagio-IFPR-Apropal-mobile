package br.com.apropal.model;

import java.io.Serializable;

public class Agricultor implements Serializable {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String cadpro;
    private String telefone;

    public Agricultor() {

    }

    public Agricultor(String id, String nome, String email, String senha, String cpf, String telefone, String cadpro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.cadpro = cadpro;
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCadpro() {
        return cadpro;
    }

    public void setCadpro(String cadpro) {
        this.cadpro = cadpro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Agricultor{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cadpro='" + cadpro + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}

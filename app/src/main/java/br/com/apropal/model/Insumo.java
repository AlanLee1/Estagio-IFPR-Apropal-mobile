package br.com.apropal.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import br.com.apropal.config.ConfiguracaoFirebase;

public class Insumo implements Serializable{

    private String id;
    private String descricao;
    private int quantidade;

    public Insumo() {

    }

    public Insumo(String id, String descricao, int quantidade) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Insumo{" +
                "id='" + id + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade + '\'' +
                '}';
    }
}

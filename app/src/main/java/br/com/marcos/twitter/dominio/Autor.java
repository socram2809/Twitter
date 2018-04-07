package br.com.marcos.twitter.dominio;

import java.io.Serializable;

/**
 * Created by Marcos on 06/04/18.
 */

public class Autor implements Serializable {

    private String nome;
    private String email;
    private String telefone;

    public Autor(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

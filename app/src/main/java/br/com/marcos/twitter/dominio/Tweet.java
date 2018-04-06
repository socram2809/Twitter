package br.com.marcos.twitter.dominio;

/**
 * Created by Marcos on 17/03/18.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * Classe de domínio que representa um Tweet
 */
public class Tweet implements Serializable{

    /**
     * Texto do Tweet
     */
    private String texto;

    /**
     * Autor do Tweet
     */
    private String autor;

    /**
     * Data de criação do Tweet
     */
    private Date data;

    /**
     * Construtor padrão do Tweet
     * @param texto
     * @param autor
     * @param data
     */
    public Tweet(String texto, String autor, Date data) {
        this.texto = texto;
        this.autor = autor;
        this.data = data;
    }

    @Override
    public String toString() {
        return "@" + autor + "\n"
                + texto + "\n"
                + "Data: " + data.toGMTString();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}

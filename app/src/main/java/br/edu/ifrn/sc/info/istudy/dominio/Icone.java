package br.edu.ifrn.sc.info.istudy.dominio;

import android.graphics.drawable.Drawable;

public class Icone {
    private String nome;
    private Drawable icone;

    public Icone(String nome, Drawable icone) {
        this.nome = nome;
        this.icone = icone;
    }

    public String getId() {
        return nome;
    }

    public void setId(String nome) {
        this.nome = nome;
    }

    public Drawable getIcone() {
        return icone;
    }

    public void setIcone(Drawable icone) {
        this.icone = icone;
    }
}

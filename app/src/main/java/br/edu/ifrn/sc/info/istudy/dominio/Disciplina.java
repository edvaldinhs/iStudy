package br.edu.ifrn.sc.info.istudy.dominio;

import java.io.Serializable;

public class Disciplina implements Serializable {

	private int id;
	private String nome;
	
	public Disciplina() {
		
	}

	public Disciplina(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}

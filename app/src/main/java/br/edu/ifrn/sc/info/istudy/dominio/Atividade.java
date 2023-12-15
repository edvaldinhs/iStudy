package br.edu.ifrn.sc.info.istudy.dominio;

public class Atividade {
	
	private int id;
	private String nome;
	private String icone;
	private Dificuldade dificuldade;
	private Conteudo conteudo;
	
	public Atividade() {
		
	}

	public Atividade(int id, String nome, String icone, Dificuldade dificuldade, Conteudo conteudo) {
		this.id = id;
		this.nome = nome;
		this.icone = icone;
		this.dificuldade = dificuldade;
		this.conteudo = conteudo;
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

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public Dificuldade getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(Dificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}

	public Conteudo getConteudo() {
		return conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}

}

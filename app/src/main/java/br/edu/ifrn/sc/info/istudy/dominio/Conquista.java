package br.edu.ifrn.sc.info.istudy.dominio;

public class Conquista {
	
	private int id;
	private String icone;
	private String nome;
	private String descricao;
	private String dataConquista;
	
	public Conquista() {
		
	}
	
	public Conquista(int id, String icone, String nome, String descricao, String dataConquista) {
		super();
		this.id = id;
		this.icone = icone;
		this.nome = nome;
		this.descricao = descricao;
		this.dataConquista = dataConquista;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIcone() {
		return icone;
	}
	public void setIcone(String icone) {
		this.icone = icone;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDataConquista() {
		return dataConquista;
	}
	public void setDataConquista(String dataConquista) {
		this.dataConquista = dataConquista;
	}
	
	
}

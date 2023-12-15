package br.edu.ifrn.sc.info.istudy.dominio;

public class Alternativa {
	
	private int id;
	private boolean respostaCerta;
	private String texto;
	private Questao questao;
	
	public Alternativa() {
		
	}

	public Alternativa(int id, boolean respostaCerta, String texto, Questao questao) {
		this.id = id;
		this.respostaCerta = respostaCerta;
		this.texto = texto;
		this.questao = questao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isRespostaCerta() {
		return respostaCerta;
	}

	public void setRespostaCerta(boolean respostaCerta) {
		this.respostaCerta = respostaCerta;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}
	
}

package br.edu.ifrn.sc.info.istudy.dominio;

public class EstudanteAtividade {
	private String email;
	private int atividadeId;
	private int numAcertos;
	private int numErros;
	private int pontuacaoTotal;
	
	public EstudanteAtividade(String email, int atividadeId, int numAcertos, int numErros, int pontuacaoTotal) {
		this.email = email;
		this.atividadeId = atividadeId;
		this.numAcertos = numAcertos;
		this.numErros = numErros;
		this.pontuacaoTotal = pontuacaoTotal;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAtividadeId() {
		return atividadeId;
	}
	public void setAtividadeId(int atividadeId) {
		this.atividadeId = atividadeId;
	}
	public int getNumAcertos() {
		return numAcertos;
	}
	public void setNumAcertos(int numAcertos) {
		this.numAcertos = numAcertos;
	}
	public int getNumErros() {
		return numErros;
	}
	public void setNumErros(int numErros) {
		this.numErros = numErros;
	}
	public int getPontuacaoTotal() {
		return pontuacaoTotal;
	}
	public void setPontuacaoTotal(int pontuacaoTotal) {
		this.pontuacaoTotal = pontuacaoTotal;
	}
	
	
	
}

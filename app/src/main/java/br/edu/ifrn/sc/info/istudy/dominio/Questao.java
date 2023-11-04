package br.edu.ifrn.sc.info.istudy.dominio;

public class Questao {
	
	private int id;
	private String enunciado;
	private int pontuacao;
	private String imagem;
	private TipoResposta tipo;
	private Atividade atividade;
	
	public Questao() {
		
	}

	public Questao(int id, String enunciado, int pontuacao, String imagem, TipoResposta tipo, Atividade atividade) {
		this.id = id;
		this.enunciado = enunciado;
		this.pontuacao = pontuacao;
		this.imagem = imagem;
		this.tipo = tipo;
		this.atividade = atividade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public TipoResposta getTipo() {
		return tipo;
	}

	public void setTipo(TipoResposta tipo) {
		this.tipo = tipo;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

}

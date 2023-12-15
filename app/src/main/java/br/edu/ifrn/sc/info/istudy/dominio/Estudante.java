package br.edu.ifrn.sc.info.istudy.dominio;

import java.util.ArrayList;
import java.util.List;

public class Estudante {

	private String email;
	private int pontuacao;
	private String nome;
	private String senha;
	private String foto;
	private Titulo titulo;
	private List<Conquista> conquistas;
	private List<Conteudo> conteudos;
	private List<Conteudo> conteudosDesbloqueados;

	public Estudante() {

	}

	public Estudante(String email, int pontuacao, String nome, String senha, String foto, Titulo titulo) {
		this.email = email;
		this.pontuacao = pontuacao;
		this.nome = nome;
		this.senha = senha;
		this.foto = foto;
		this.titulo = titulo;
		conquistas = new ArrayList<>();
		conteudos = new ArrayList<>();
		this.conteudosDesbloqueados = new ArrayList<>();

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public List<Conquista> getConquistas() {
		return conquistas;
	}

	public void setConquistas(List<Conquista> conquistas) {
		this.conquistas = conquistas;
	}

	public List<Conteudo> getConteudos() {
		return conteudos;
	}

	public void setConteudos(List<Conteudo> conteudos) {
		this.conteudos = conteudos;
	}

	public void adicionarConteudoDesbloqueado(Conteudo conteudo) {
		conteudosDesbloqueados.add(conteudo);
	}

	public List<Conteudo> getConteudosDesbloqueados() {
		return conteudosDesbloqueados;
	}

}

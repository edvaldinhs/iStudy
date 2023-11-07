package br.edu.ifrn.sc.info.istudy.dominio;

public class RequestConteudo {
	    private String email;
	    private int conteudoId;
	    
	    public RequestConteudo() {
			
		}
	    
		public RequestConteudo(String email, int conteudoId) {
			this.email = email;
			this.conteudoId = conteudoId;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getConteudoId() {
			return conteudoId;
		}

		public void setConteudoId(int conteudoId) {
			this.conteudoId = conteudoId;
		}
		
		

	    
}

package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ConteudoWS {

    @GET("conteudos/listarTodos")
    Call<List<Conteudo>> listarTodos();

}

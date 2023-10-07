package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConteudoWS {

    @GET("conteudos/listarTodos")
    Call< List<Conteudo> > listarTodos();

    @POST("conteudos/inserir")
    Call< Boolean > inserir(@Body Conteudo conteudo);

    @PUT("conteudos/atualizar")
    Call< Boolean > atualizar(@Body Conteudo conteudo);

    @DELETE("conteudos/remover/{id}")
    Call< Boolean > remover(@Path("id") int id);

    @GET("conteudos/buscar/{id}")
    Call< Conteudo > buscar(@Path("id") int id);

}

package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Titulo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TituloWS {

    @GET("titulos/listarTodos")
    Call<List<Titulo>> listarTodos();

    @POST("titulos/inserir")
    Call< Boolean > inserir(@Body Titulo titulo);

    @PUT("titulos/atualizar")
    Call< Boolean > atualizar(@Body Titulo titulo);

    @DELETE("titulos/remover/{id}")
    Call< Boolean > remover(@Path("id") int id);

    @GET("titulos/buscar/{id}")
    Call< Titulo > buscar(@Path("id") int id);

}

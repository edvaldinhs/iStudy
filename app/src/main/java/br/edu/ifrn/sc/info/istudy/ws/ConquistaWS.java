package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Conquista;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConquistaWS {
    @GET("conquistas/listarTodas")
    Call<List<Conquista>> listarTodas();

    @POST("conquistas/inserir")
    Call< Boolean > inserir(@Body Conquista conquista);

    @PUT("conquistas/atualizar")
    Call< Boolean > atualizar(@Body Conquista conquista);

    @DELETE("conquistas/remover/{id}")
    Call< Boolean > remover(@Path("id") int id);

    @GET("conquistas/buscar/{id}")
    Call< Conquista > buscar(@Path("id") int id);
}

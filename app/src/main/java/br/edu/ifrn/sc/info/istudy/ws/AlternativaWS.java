package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Alternativa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlternativaWS {

    @GET("alternativas/listarTodas")
    Call<List<Alternativa>> listarTodas();

    @POST("alternativas/inserir")
    Call< Boolean > inserir(@Body Alternativa alternativa);

    @PUT("alternativas/atualizar")
    Call< Boolean > atualizar(@Body Alternativa alternativa);

    @DELETE("alternativas/remover/{id}")
    Call< Boolean > remover(@Path("id") int id);

    @GET("alternativas/buscar/{id}")
    Call< Alternativa > buscar(@Path("id") int id);

    @GET("alternativas/buscarPorQuestao/{id}")
    Call< List<Alternativa> > buscarPorQuestao(@Path("id") int id);
}

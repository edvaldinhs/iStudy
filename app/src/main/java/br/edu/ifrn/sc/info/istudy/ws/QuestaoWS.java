package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Questao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface QuestaoWS {

    @GET("questoes/listarTodas")
    Call<List<Questao>> listarTodas();

    @POST("questoes/inserir")
    Call< Boolean > inserir(@Body Questao questao);

    @PUT("questoes/atualizar")
    Call< Boolean > atualizar(@Body Questao questao);

    @DELETE("questoes/remover/{id}")
    Call< Boolean > remover(@Path("id") int id);

    @GET("questoes/buscar/{id}")
    Call< Questao > buscar(@Path("id") int id);

    @GET("questoes/buscarPorAtividade/{id}")
    Call< List<Questao> > buscarPorAtividade(@Path("id") int id);
}

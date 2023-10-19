package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Atividade;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AtividadeWS {

    @GET("atividades/listarTodas")
    Call< List<Atividade> > listarTodas();

    @POST("atividades/inserir")
    Call< Boolean > inserir(@Body Atividade atividade);

    @PUT("atividades/atualizar")
    Call< Boolean > atualizar(@Body Atividade atividade);

    @DELETE("atividades/remover/{id}")
    Call< Boolean > remover(@Path("id") int id);

    @GET("atividades/buscar/{id}")
    Call< Atividade > buscar(@Path("id") int id);

}

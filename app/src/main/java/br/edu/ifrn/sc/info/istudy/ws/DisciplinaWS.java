package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DisciplinaWS {

    @GET("disciplinas/listarTodas")
    Call< List<Disciplina> > listarTodas();

    @POST("disciplinas/inserir")
    Call< Boolean > inserir(@Body Disciplina disciplina);

    @PUT("disciplinas/atualizar")
    Call< Boolean > atualizar(@Body Disciplina disciplina);

    @DELETE("disciplinas/remover/{id}")
    Call< Boolean > remover(@Path("id") int id);

    @GET("disciplinas/buscar/{id}")
    Call< Disciplina > buscar(@Path("id") int id);
}

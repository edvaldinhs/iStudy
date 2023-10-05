package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EstudanteWS {

    @GET("estudantes/listarTodos")
    Call<List<Estudante>> listarTodos();

    @POST("estudantes/inserir")
    Call< Boolean > inserir(@Body Estudante estudante);

    @PUT("estudantes/atualizar")
    Call< Boolean > atualizar(@Body Estudante estudante);

    @DELETE("estudantes/remover/{email}")
    Call< Boolean > remover(@Path("email") String email);

    @GET("estudantes/buscar/{email}")
    Call< Estudante > buscar(@Path("email") String email);

}

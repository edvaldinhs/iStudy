package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
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

    @GET("conteudos/listarTodosDesbloqueados")
    Call< List<Conteudo> > listarTodosDesbloqueados(@Body Estudante estudante);

    @POST("conteudos/inserir")
    Call< Boolean > inserir(@Body Conteudo conteudo);

    @PUT("conteudos/atualizar")
    Call< Boolean > atualizar(@Body Conteudo conteudo);

    @PUT("conteudos/desbloquearConteudo")
    Call< Boolean > desbloquearConteudo(@Body int id);

    @DELETE("conteudos/remover/{id}")
    Call< Boolean > remover(@Path("id") int id);

    @GET("conteudos/buscar/{id}")
    Call< Conteudo > buscar(@Path("id") int id);

    @GET("conteudos/buscar/{email}-{conteudoId}")
    Call< Integer > buscarProgressoConteudo(@Path("email") String email ,@Path("conteudoId") int conteudoId);

}

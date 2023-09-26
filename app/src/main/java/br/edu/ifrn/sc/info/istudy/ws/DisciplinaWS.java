package br.edu.ifrn.sc.info.istudy.ws;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DisciplinaWS {

    @GET("disciplinas/listarTodas")
    Call< List<Disciplina> > listarTodas();

    @POST("disciplinas/inserir")
    Call< Boolean >  inserir(@Body Disciplina disciplina);
}

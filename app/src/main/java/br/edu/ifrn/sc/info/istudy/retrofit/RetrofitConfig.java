package br.edu.ifrn.sc.info.istudy.retrofit;

import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://10.161.1.74:8080/iStudyServer/webapi/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public ConteudoWS getConteudoWS() {
        return retrofit.create(ConteudoWS.class);
    }
    public DisciplinaWS getDisciplinaWS() {
        return retrofit.create(DisciplinaWS.class);
    }
}

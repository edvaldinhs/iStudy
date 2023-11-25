package br.edu.ifrn.sc.info.istudy.retrofit;

import br.edu.ifrn.sc.info.istudy.ws.AlternativaWS;
import br.edu.ifrn.sc.info.istudy.ws.AtividadeWS;
import br.edu.ifrn.sc.info.istudy.ws.ConquistaWS;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import br.edu.ifrn.sc.info.istudy.ws.QuestaoWS;
import br.edu.ifrn.sc.info.istudy.ws.TituloWS;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://54.233.154.91:8080/iStudyServer/webapi/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public ConteudoWS getConteudoWS() {
        return retrofit.create(ConteudoWS.class);
    }
    public DisciplinaWS getDisciplinaWS() {
        return retrofit.create(DisciplinaWS.class);
    }
    public EstudanteWS getEstudanteWS() {
        return retrofit.create(EstudanteWS.class);
    }
    public TituloWS getTituloWS() {
        return retrofit.create(TituloWS.class);
    }
    public ConquistaWS getConquistaWS() {
        return retrofit.create(ConquistaWS.class);
    }
    public AtividadeWS getAtividadeWS() {
        return retrofit.create(AtividadeWS.class);
    }
    public QuestaoWS getQuestaoWS() {
        return retrofit.create(QuestaoWS.class);
    }
    public AlternativaWS getAlternativaWS() {
        return retrofit.create(AlternativaWS.class);
    }

}

package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConteudoDescricao extends Fragment {

    private ConstraintLayout clTitulo;

    private ConstraintLayout clTts;

    private TextView tvTituloConteudo;

    private LinearLayout clDescricao;

    private ImageView botaoTts;

    private TextView tvDescricao;

    private TextToSpeech tts;

    private Bundle extras;

    private int id;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ConteudoDescricao() {

    }

    public static ConteudoDescricao newInstance(String param1, String param2) {
        ConteudoDescricao fragment = new ConteudoDescricao();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conteudo_descricao, container, false);

        clTts = view.findViewById(R.id.clTts);
        clDescricao = view.findViewById(R.id.descricao);
        tvTituloConteudo = view.findViewById(R.id.tvTitulo_conteudos);

        botaoTts = view.findViewById(R.id.botaoTts);
        tvDescricao = view.findViewById(R.id.tvResumo);

        extras = getArguments();

        if(extras != null){
            id = extras.getInt("conteudoId");
            setarNomeConteudosPeloID(id);
        }

        String texto1 = "\t\t\tA variação linguística corresponde às diferentes maneiras de expressão de uma língua. Ela existe porque as línguas possuem a característica de serem sensíveis a fatores como: grau de formalidade, região geográfica, período histórico, sexo e classe social.\n\n" +
                "\t\t\tCom relação ao grau de formalidade, a língua pode ser formal ou informal. A linguagem formal é aquela que usa corretamente as regras gramaticais; a linguagem informal ou coloquial é aquela do cotidiano. \n\n" +
                "\t\t\tAlém disso, ainda existe a variação das expressões de acordo com o sentido denotativo ou conotativo: denotativo se refere ao sentido literal de uma palavra, e o conotativo, ao sentido figurado.\n\n";

        tvDescricao.setText(texto1);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                   int linguagem = tts.setLanguage(Locale.forLanguageTag("pt-BR"));
                }
            }
        });

        botaoTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tts.isSpeaking()){
                    String texto = tvDescricao.getText().toString();
                    int falar = tts.speak(texto, tts.QUEUE_FLUSH, null);
                }else{
                    tts.stop();
                }
            }
        });

        return view;
    }

    public void setarNomeConteudosPeloID(int id) {
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Conteudo> metodoBuscar = conteudoWS.buscar(id);

        metodoBuscar.enqueue(new Callback<Conteudo>() {
            @Override
            public void onResponse(Call<Conteudo> call, Response<Conteudo> response) {
                Conteudo conteudo = response.body();
                tvTituloConteudo.setText(conteudo.getNome());
            }

            @Override
            public void onFailure(Call<Conteudo> call, Throwable t) {

            }
        });
    }
}
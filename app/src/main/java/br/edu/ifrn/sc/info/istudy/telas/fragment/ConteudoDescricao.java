package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.dominio.RequestConteudo;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConteudoDescricao extends Fragment {

    private ConstraintLayout clTitulo;

    private ConstraintLayout clTts;

    private String email;

    private TextView viewResumo;
    private TextView tvTituloConteudo;

    private LinearLayout clDescricao;

    private ImageView botaoTts;

    private TextView tvDescricao;

    private TextToSpeech tts;

    private Button btnFinalizarConteudo;

    private Bundle extras;

    private NavController navController;

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

        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        viewResumo = view.findViewById(R.id.viewResumo);
        clTts = view.findViewById(R.id.clTts);
        clDescricao = view.findViewById(R.id.descricao);
        tvTituloConteudo = view.findViewById(R.id.tvTitulo_conteudos);

        botaoTts = view.findViewById(R.id.botaoTts);
        tvDescricao = view.findViewById(R.id.tvResumo);

        btnFinalizarConteudo = view.findViewById(R.id.btnFinalizarConteudo);

        extras = getArguments();

        if(extras != null){
            id = extras.getInt("conteudoId");
            email = extras.getString("email");
            setarNomeConteudosPeloID(id);
        }

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

        btnFinalizarConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarConteudo();
            }
        });

        return view;
    }

    public void finalizarConteudo(){
        Bundle cartinha = new Bundle();

        cartinha.putInt("conteudoId", id);
        cartinha.putString("email", email);

        try {
            finalizarConteudoWS(email, id);
        }catch (IllegalArgumentException illegalArgumentException){
            Log.e("ConteudoDescricao", illegalArgumentException.getMessage());
        }

        if (navController != null) {
            navController.navigate(R.id.action_conteudoDescricao_to_atividades, cartinha);
            Log.d("ConteudoDescricaoFrag", navController.toString());
        } else {
            Log.e("ConteudoDescricaoFrag", "NavController is null");
        }
    }

    public void finalizarConteudoWS(String email, int conteudoId) {
        RequestConteudo requestConteudo = new RequestConteudo(email, conteudoId);

        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Boolean> finalizar = conteudoWS.finalizar(requestConteudo);

        finalizar.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void setarNomeConteudosPeloID(int id) {
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Conteudo> metodoBuscar = conteudoWS.buscar(id);

        metodoBuscar.enqueue(new Callback<Conteudo>() {
            @Override
            public void onResponse(Call<Conteudo> call, Response<Conteudo> response) {
                Conteudo conteudo = response.body();
                if(conteudo.getDisciplina().getId() == 1){
                   viewResumo.setBackgroundResource(R.drawable.borda_redonda_port);
                   btnFinalizarConteudo.setBackgroundColor(getResources().getColor(R.color.istudy_roxo));
                }else if(conteudo.getDisciplina().getId() == 2) {
                    viewResumo.setBackgroundResource(R.drawable.borda_redonda_mat);
                    btnFinalizarConteudo.setBackgroundColor(getResources().getColor(R.color.istudy_verde));
                }

                tvTituloConteudo.setText(conteudo.getNome());
                tvDescricao.setText(conteudo.getResumo());
            }

            @Override
            public void onFailure(Call<Conteudo> call, Throwable t) {

            }
        });
    }
}
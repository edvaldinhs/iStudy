package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Atividades extends Fragment {

    private Bundle extras;

    private int id;

    private ConstraintLayout clReferencia;

    private TextView tvConteudo;
    private TextView tvDisciplina;

    private ConstraintLayout cardQuizFacil;
    private ConstraintLayout cardQuizMedio;
    private ConstraintLayout cardQuizDificil;

    private NavController navController;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Atividades() {

    }

    public static Atividades newInstance(String param1, String param2) {
        Atividades fragment = new Atividades();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atividades, container, false);

        clReferencia = view.findViewById(R.id.clReferencia);

        tvConteudo = view.findViewById(R.id.tvConteudo);
        tvDisciplina = view.findViewById(R.id.tvDisciplina);

        cardQuizFacil = view.findViewById(R.id.cardQuizFacil);
        cardQuizMedio = view.findViewById(R.id.cardQuizMedio);
        cardQuizDificil = view.findViewById(R.id.cardQuizDificil);

        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        extras = getArguments();

        if(extras != null){
            id = extras.getInt("conteudoId");
            personalizarTela(id);
        }

        iniciarQuizListener();

        return view;
    }

    public void personalizarTela(int id) {
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Conteudo> metodoBuscar = conteudoWS.buscar(id);

        metodoBuscar.enqueue(new Callback<Conteudo>() {
            @Override
            public void onResponse(Call<Conteudo> call, Response<Conteudo> response) {
                Conteudo conteudo = response.body();
                try {
                    if(conteudo.getDisciplina().getId() == 1){
                        clReferencia.setBackgroundResource(R.drawable.bg_atividade_port);
                    }else if (conteudo.getDisciplina().getId() == 2){
                        clReferencia.setBackgroundResource(R.drawable.bg_atividade_mat);
                    }
                    verificarBloqueado(conteudo.getDisciplina().getId());

                    tvConteudo.setText(conteudo.getNome());
                    tvDisciplina.setText(conteudo.getDisciplina().getNome());
                }catch (NullPointerException nullPointerException){
                    Log.e("Atividades", nullPointerException.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Conteudo> call, Throwable t) {

            }
        });
    }

    public void verificarBloqueado(int disciplinaId){
        if(disciplinaId == 1){
            cardQuizFacil.setBackgroundResource(R.drawable.card_dificuldade_port);
        }else if (disciplinaId == 2){
            cardQuizFacil.setBackgroundResource(R.drawable.card_dificuldade_mat);
        }
    }

    public void iniciarQuizListener(){
        cardQuizFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarQuiz(1);
            }
        });
        cardQuizMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarQuiz(2);
            }
        });
        cardQuizDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarQuiz(3);
            }
        });
    }
    public void iniciarQuiz(int dificuldadeId){
        Bundle cartinha = new Bundle();

        cartinha.putInt("conteudoId", id);
        cartinha.putInt("dificuldadeId", dificuldadeId);

        if (navController != null) {
            navController.navigate(R.id.action_atividades_to_multiplaEscolha, cartinha);
            Log.d("ConteudoDescricaoFrag", navController.toString());
        } else {
            Log.e("ConteudoDescricaoFrag", "NavController is null");
        }
    }
}
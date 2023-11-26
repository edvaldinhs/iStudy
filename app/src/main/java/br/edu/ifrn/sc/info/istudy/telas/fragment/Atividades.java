package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous.AvisoDialog;
import br.edu.ifrn.sc.info.istudy.dominio.Atividade;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Atividades extends Fragment {

    private Bundle extras;

    private int id;

    private String email;

    private ConstraintLayout clReferencia;

    private List<Atividade> atividades = new ArrayList<>();

    private TextView tvConteudo;
    private TextView tvDisciplina;

    Conteudo conteudo;

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
            email = extras.getString("email");
            Log.d("PGCT", "email: "+ email);
            personalizarTela(id);
        }

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
                    bloqueioAtividade(email, id, conteudo.getDisciplina().getId());

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

    public void bloqueioAtividade(String email, int id, int disciplinaId){
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Integer> metodoBuscar = conteudoWS.buscarProgressoConteudo(email, id);

        metodoBuscar.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("PGCT", response.body()+"");
                iniciarQuizListener(response.body(), disciplinaId);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("PGCT", t.getMessage());
            }
        });
    }

    public void iniciarQuizListener(int progressoConteudo, int disciplinaId){
        Log.d("PGCT", progressoConteudo+"");
        if (progressoConteudo >= 1) {
            cardQuizFacil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iniciarQuiz(1);
                }
            });
            if (disciplinaId == 1) {
                cardQuizFacil.setBackgroundResource(R.drawable.card_dificuldade_port);
            } else if (disciplinaId == 2) {
                cardQuizFacil.setBackgroundResource(R.drawable.card_dificuldade_mat);
            }
        } else {
            cardQuizFacil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AvisoDialog(getActivity(), "Bloqueado!").iniciarAvisoDialog();
                }
            });
        }

        if (progressoConteudo >= 2) {
            cardQuizMedio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iniciarQuiz(2);
                }
            });
            if (disciplinaId == 1) {
                cardQuizMedio.setBackgroundResource(R.drawable.card_dificuldade_port);
            } else if (disciplinaId == 2) {
                cardQuizMedio.setBackgroundResource(R.drawable.card_dificuldade_mat);
            }
        } else {
            cardQuizMedio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AvisoDialog(getActivity(), "Bloqueado!").iniciarAvisoDialog();
                }
            });
        }

        if (progressoConteudo >= 3) {
            cardQuizDificil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iniciarQuiz(3);
                }
            });
            if (disciplinaId == 1) {
                cardQuizDificil.setBackgroundResource(R.drawable.card_dificuldade_port);
            } else if (disciplinaId == 2) {
                cardQuizDificil.setBackgroundResource(R.drawable.card_dificuldade_mat);
            }
        } else {
            cardQuizDificil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AvisoDialog(getActivity(), "Bloqueado!").iniciarAvisoDialog();
                }
            });
        }
    }
    public void iniciarQuiz(int dificuldadeId){
        Bundle cartinha = new Bundle();

        cartinha.putInt("conteudoId", id);
        cartinha.putInt("dificuldadeId", dificuldadeId);
        cartinha.putString("email", email);

        if (navController != null) {
            navController.navigate(R.id.action_atividades_to_multiplaEscolha, cartinha);
            Log.d("ConteudoDescricaoFrag", navController.toString());
        } else {
            Log.e("ConteudoDescricaoFrag", "NavController is null");
        }
    }
}
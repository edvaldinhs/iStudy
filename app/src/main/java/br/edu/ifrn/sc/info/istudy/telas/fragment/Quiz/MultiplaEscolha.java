package br.edu.ifrn.sc.info.istudy.telas.fragment.Quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Alternativa;
import br.edu.ifrn.sc.info.istudy.dominio.Atividade;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.dominio.Questao;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.telas.TelaInicial;
import br.edu.ifrn.sc.info.istudy.ws.AlternativaWS;
import br.edu.ifrn.sc.info.istudy.ws.AtividadeWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import br.edu.ifrn.sc.info.istudy.ws.QuestaoWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MultiplaEscolha extends Fragment {

    private List<Questao> questoes = new ArrayList<>();

    private List<Alternativa> alternativas = new ArrayList<>();

    NavController navController;

    private TextView tvTextoQuestao;

    int questaoAtual;

    int conteudoId;
    int dificuldadeId;

    private Bundle extras;

    boolean respostaEscolhida;

    private View botaoClicado;

    private ConstraintLayout escolhaA;
    private ConstraintLayout escolhaB;
    private ConstraintLayout escolhaC;
    private ConstraintLayout escolhaD;

    private TextView tvRespostaA;
    private TextView tvRespostaB;
    private TextView tvRespostaC;
    private TextView tvRespostaD;

    private Button btnFinalizarConteudo;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MultiplaEscolha() {

    }

    public static MultiplaEscolha newInstance(String param1, String param2) {
        MultiplaEscolha fragment = new MultiplaEscolha();
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
        View view = inflater.inflate(R.layout.fragment_multipla_escolha, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        questaoAtual = 0;

        extras = getArguments();

        if(extras != null){
            conteudoId = extras.getInt("conteudoId");
            dificuldadeId = extras.getInt("dificuldadeId");
        }

        tvTextoQuestao = view.findViewById(R.id.tvTextoQuestao);

        escolhaA = view.findViewById(R.id.clRespostaA);
        escolhaB = view.findViewById(R.id.clRespostaB);
        escolhaC = view.findViewById(R.id.clRespostaC);
        escolhaD = view.findViewById(R.id.clRespostaD);

        tvRespostaA = view.findViewById(R.id.tvTextoRespostaA);
        tvRespostaB = view.findViewById(R.id.tvTextoRespostaB);
        tvRespostaC = view.findViewById(R.id.tvTextoRespostaC);
        tvRespostaD = view.findViewById(R.id.tvTextoRespostaD);

        btnFinalizarConteudo = view.findViewById(R.id.btnFinalizarConteudo);

        iniciarQuiz();

        alternativaClickListener();

        btnFinalizarConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botaoClicado != null){
                    respostaSelecionada(botaoClicado);
                    proximaQuestao();
                    botaoClicado = null;
                }else {
                    Toast.makeText(getActivity(), "Selecione uma Resposta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void iniciarQuiz() {
            gerarQuestoes();
    }

    private void gerarQuestoes() {
        RetrofitConfig config = new RetrofitConfig();
        AtividadeWS atividadeWS = config.getAtividadeWS();
        Call<List<Atividade>> metodoBuscar = atividadeWS.buscarPeloConteudo(conteudoId);

        metodoBuscar.enqueue(new Callback<List<Atividade>>() {
            @Override
            public void onResponse(Call<List<Atividade>> call, Response<List<Atividade>> response) {
                int atvId = -1;
                for (Atividade atividade : response.body()){
                    if(atividade.getDificuldade().getId() == dificuldadeId){
                        atvId = atividade.getId();
                    }
                }
                if(atvId != -1){
                    preencherQuestoes(atvId);
                }
            }

            @Override
            public void onFailure(Call<List<Atividade>> call, Throwable t) {
                Log.e("QuizME",t.getMessage());
            }
        });

    }

    public void respostaSelecionada(View view){

        view.setBackgroundColor(Color.parseColor("#17243e"));

        escolhaA.setEnabled(false);
        escolhaB.setEnabled(false);
        escolhaC.setEnabled(false);
        escolhaD.setEnabled(false);

    }

    public void proximaQuestao(){
        resetarBotoes();
        questaoAtual++;

        if (questaoAtual > questoes.size() - 1){

            if (navController != null) {
                navController.navigate(R.id.action_multiplaEscolha_to_conteudo);
                Log.d("QuizME", navController.toString());
            } else {
                Log.e("QuizME", "NavController is null");
            }
        } else {
            iniciarQuiz();
        }

    }

    private void alternativaClickListener(){

        escolhaA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                respostaEscolhida = alternativas.get(0).isRespostaCerta();
                mudarCorOnClick(v);
                botaoClicado = v;
            }
        });
        escolhaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                respostaEscolhida = alternativas.get(1).isRespostaCerta();
                mudarCorOnClick(v);
                botaoClicado = v;
            }
        });
        escolhaC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                respostaEscolhida = alternativas.get(2).isRespostaCerta();
                mudarCorOnClick(v);
                botaoClicado = v;
            }
        });
        escolhaD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                respostaEscolhida = alternativas.get(3).isRespostaCerta();
                mudarCorOnClick(v);
                botaoClicado = v;
            }
        });
    }

    public void mudarCorOnClick(View view){
        int pL = escolhaA.getPaddingLeft();
        int pT = escolhaA.getPaddingTop();
        int pR = escolhaA.getPaddingRight();
        int pB = escolhaA.getPaddingBottom();

        escolhaA.setBackgroundResource(R.drawable.borda);
        escolhaA.setPadding(pL, pT, pR, pB);
        escolhaB.setBackgroundResource(R.drawable.borda);
        escolhaB.setPadding(pL, pT, pR, pB);
        escolhaC.setBackgroundResource(R.drawable.borda);
        escolhaC.setPadding(pL, pT, pR, pB);
        escolhaD.setBackgroundResource(R.drawable.borda);
        escolhaD.setPadding(pL, pT, pR, pB);

        view.setBackgroundResource(R.drawable.borda_port);
        view.setPadding(pL, pT, pR, pB);
    }

    public void resetarBotoes(){

        int pL = escolhaA.getPaddingLeft();
        int pT = escolhaA.getPaddingTop();
        int pR = escolhaA.getPaddingRight();
        int pB = escolhaA.getPaddingBottom();

        escolhaA.setBackgroundResource(R.drawable.borda);
        escolhaA.setPadding(pL, pT, pR, pB);
        escolhaB.setBackgroundResource(R.drawable.borda);
        escolhaB.setPadding(pL, pT, pR, pB);
        escolhaC.setBackgroundResource(R.drawable.borda);
        escolhaC.setPadding(pL, pT, pR, pB);
        escolhaD.setBackgroundResource(R.drawable.borda);
        escolhaD.setPadding(pL, pT, pR, pB);

        escolhaA.setEnabled(true);
        escolhaB.setEnabled(true);
        escolhaC.setEnabled(true);
        escolhaD.setEnabled(true);
    }

    private void preencherQuestoes(int id){
        RetrofitConfig config = new RetrofitConfig();
        QuestaoWS questaoWS = config.getQuestaoWS();
        Call<List<Questao>> metodoBuscar = questaoWS.buscarPorAtividade(id);

        metodoBuscar.enqueue(new Callback<List<Questao>>() {
            @Override
            public void onResponse(Call<List<Questao>> call, Response<List<Questao>> response) {
                questoes = response.body();
                String textoDaQuestao = questoes.get(questaoAtual).getEnunciado();
                tvTextoQuestao.setText(textoDaQuestao);
                preencherAlternativas(questoes.get(questaoAtual).getId());
            }

            @Override
            public void onFailure(Call<List<Questao>> call, Throwable t) {
                Log.e("QuizME", t.getMessage());
            }
        });

    }

    public void preencherAlternativas(int id){
        RetrofitConfig config = new RetrofitConfig();
        AlternativaWS alternativaWS = config.getAlternativaWS();
        Call<List<Alternativa>> metodoBuscar = alternativaWS.buscarPorQuestao(id);

        metodoBuscar.enqueue(new Callback<List<Alternativa>>() {
            @Override
            public void onResponse(Call<List<Alternativa>> call, Response<List<Alternativa>> response) {
                alternativas = response.body();

                tvRespostaA.setText(alternativas.get(0).getTexto());
                tvRespostaB.setText(alternativas.get(1).getTexto());
                tvRespostaC.setText(alternativas.get(2).getTexto());
                tvRespostaD.setText(alternativas.get(3).getTexto());
            }

            @Override
            public void onFailure(Call<List<Alternativa>> call, Throwable t) {
                Log.e("QuizME", t.getMessage());
            }
        });
    }


}
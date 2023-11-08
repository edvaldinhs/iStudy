package br.edu.ifrn.sc.info.istudy.telas.fragment.Quiz;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.SheetDialog.CarregandoDialog;
import br.edu.ifrn.sc.info.istudy.dominio.Alternativa;
import br.edu.ifrn.sc.info.istudy.dominio.Atividade;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Questao;
import br.edu.ifrn.sc.info.istudy.dominio.RequestConteudo;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.AlternativaWS;
import br.edu.ifrn.sc.info.istudy.ws.AtividadeWS;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.QuestaoWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MultiplaEscolha extends Fragment {

    private List<Questao> questoes = new ArrayList<>();

    private List<Alternativa> alternativas = new ArrayList<>();

    private int quantidadeAlternativa = -1;

    private CarregandoDialog carregandoDialog;

    private Conteudo conteudo;

    private int numAcertos;

    private NavController navController;

    private TextView tvTextoQuestao;

    private int questaoAtual;

    private int conteudoId;
    private int dificuldadeId;

    private ConstraintLayout escolhas;
    private Bundle extras;

    private boolean respostaEscolhida;

    private ImageView acerto;
    private ImageView acerto_mat;
    private ImageView erro;
    private AnimatedVectorDrawableCompat avd;
    private AnimatedVectorDrawable avd2;

    private View botaoClicado;

    private ConstraintLayout escolhaA;
    private ConstraintLayout escolhaB;
    private ConstraintLayout escolhaC;
    private ConstraintLayout escolhaD;

    private TextView icRespostaA;
    private TextView icRespostaB;

    private TextView tvRespostaA;
    private TextView tvRespostaB;
    private TextView tvRespostaC;
    private TextView tvRespostaD;

    private TextView tvNivel;

    private TextView nQuestao1;
    private TextView nQuestao2;
    private TextView nQuestao3;
    private TextView nQuestao4;
    private TextView nQuestao5;

    private Button btnFinalizarConteudo;

    private int originalWidth;
    private int originalHeight;
    private int larguraOriginal;
    private int alturaOriginal;
    private int larguraFinal;
    private int alturaFinal;

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

        carregandoDialog = new CarregandoDialog(requireActivity());

        tvNivel = view.findViewById(R.id.tvNivel);

        nQuestao1 = view.findViewById(R.id.nQuestao1);
        nQuestao2 = view.findViewById(R.id.nQuestao2);
        nQuestao3 = view.findViewById(R.id.nQuestao3);
        nQuestao4 = view.findViewById(R.id.nQuestao4);
        nQuestao5 = view.findViewById(R.id.nQuestao5);

        escolhas = view.findViewById(R.id.escolhas);

        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        questaoAtual = 0;

        extras = getArguments();

        carregandoDialog.iniciarCarregandoDialog();

        if(extras != null){
            conteudoId = extras.getInt("conteudoId");
            dificuldadeId = extras.getInt("dificuldadeId");
            personalizarTela(conteudoId);
        }

        acerto = view.findViewById(R.id.acerto_port);
        acerto_mat = view.findViewById(R.id.acerto_mat);
        erro = view.findViewById(R.id.erro);
        acerto.setVisibility(View.INVISIBLE);
        erro.setVisibility(View.INVISIBLE);

        tvTextoQuestao = view.findViewById(R.id.tvTextoQuestao);

        escolhaA = view.findViewById(R.id.clRespostaA);
        escolhaB = view.findViewById(R.id.clRespostaB);
        escolhaC = view.findViewById(R.id.clRespostaC);
        escolhaD = view.findViewById(R.id.clRespostaD);

        icRespostaA = view.findViewById(R.id.tvRespostaA);
        icRespostaB = view.findViewById(R.id.tvRespostaB);
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
                    verificarAcerto();

                    botaoClicado = null;
                }else {
                    Toast.makeText(getActivity(), "Selecione uma Resposta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void verificarAcerto(){
        Drawable drawable = acerto.getDrawable();
        if(respostaEscolhida) {

            numAcertos++;

//            if(conteudo!=null){
//                if(conteudo.getDisciplina().getId() == 1){
//                    drawable = acerto_mat.getDrawable();
//                }else if(conteudo.getDisciplina().getId() == 2){
//                    drawable = acerto.getDrawable();
//                }
//            }else{
//                drawable = acerto.getDrawable();
//            }
//            if(drawable instanceof AnimatedVectorDrawableCompat){
//                avd = (AnimatedVectorDrawableCompat) drawable;
//                acerto.setVisibility(View.VISIBLE);
//                avd.start();
//            } else if (drawable instanceof AnimatedVectorDrawable) {
//                avd2 = (AnimatedVectorDrawable) drawable;
//                acerto.setVisibility(View.VISIBLE);
//                avd2.start();
//            }
//            Log.d("verificarQuestao", "Acertou!");
//        }else{
//            drawable = erro.getDrawable();
//            if(drawable instanceof AnimatedVectorDrawableCompat){
//                avd = (AnimatedVectorDrawableCompat) drawable;
//                erro.setVisibility(View.VISIBLE);
//                avd.start();
//            } else if (drawable instanceof AnimatedVectorDrawable) {
//                avd2 = (AnimatedVectorDrawable) drawable;
//                erro.setVisibility(View.VISIBLE);
//                avd2.start();
//            }
//            Log.d("verificarQuestao", "Errou feio, errou rude.");
        }
        carregandoDialog.iniciarCarregandoDialog();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                acerto.setVisibility(View.INVISIBLE);
                erro.setVisibility(View.INVISIBLE);
                timer.cancel();
                carregandoDialog.removerDialog();

                proximaQuestao();
            }
        }, 500); // Delay em millisegundos (2 segundos)
    }

    private void iniciarQuiz() {
        atualizarNumQuestao();
        carregandoDialog.removerDialog();
        carregandoDialog.iniciarCarregandoDialog();
        gerarQuestoes();

    }


    public void atualizarNumQuestao(){
        nQuestao1.setBackgroundResource(R.drawable.quadrado_cinza);
        nQuestao2.setBackgroundResource(R.drawable.quadrado_cinza);
        nQuestao3.setBackgroundResource(R.drawable.quadrado_cinza);
        nQuestao4.setBackgroundResource(R.drawable.quadrado_cinza);
        nQuestao5.setBackgroundResource(R.drawable.quadrado_cinza);
        switch (questaoAtual + 1){
            case 1:
                nQuestao1.setBackgroundResource(R.drawable.quadrado_azul);
                break;
            case 2:
                nQuestao2.setBackgroundResource(R.drawable.quadrado_azul);
                break;
            case 3:
                nQuestao3.setBackgroundResource(R.drawable.quadrado_azul);
                break;
            case 4:
                nQuestao4.setBackgroundResource(R.drawable.quadrado_azul);
                break;
            case 5:
                nQuestao5.setBackgroundResource(R.drawable.quadrado_azul);
                break;
            default:
                break;
        }
    }

    private void gerarQuestoes() {
        RetrofitConfig config = new RetrofitConfig();
        AtividadeWS atividadeWS = config.getAtividadeWS();
        Call<List<Atividade>> metodoBuscar = atividadeWS.buscarPeloConteudo(conteudoId);

        metodoBuscar.enqueue(new Callback<List<Atividade>>() {
            @Override
            public void onResponse(Call<List<Atividade>> call, Response<List<Atividade>> response) {

                try {
                    int atvId = -1;
                    for (Atividade atividade : response.body()){
                        if(atividade.getDificuldade().getId() == dificuldadeId){
                            atvId = atividade.getId();
                        }
                    }
                    if(atvId != -1){
                        preencherQuestoes(atvId);
                    }
                }catch (NullPointerException nullPointerException){

                    Log.e("QuizMe", nullPointerException.getMessage());

                }
            }

            @Override
            public void onFailure(Call<List<Atividade>> call, Throwable t) {
                Log.e("QuizME",t.getMessage());
            }
        });

    }

    public void respostaSelecionada(View view){
        escolhaA.setEnabled(false);
        escolhaB.setEnabled(false);
        escolhaC.setEnabled(false);
        escolhaD.setEnabled(false);
    }

    public void proximaQuestao() {

        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    resetarBotoes();
                    questaoAtual++;
                    if (questaoAtual > questoes.size() - 1) {
                        if (numAcertos >= 3) {
                            verificadorDeDesbloqueio("estudante@gmail.com", conteudoId, dificuldadeId);
                        }

                        Bundle cartinha = new Bundle();

                        cartinha.putInt("conteudoId", conteudoId);

                        if (navController != null) {
                            navController.navigate(R.id.action_multiplaEscolha_to_atividades, cartinha);
                            Log.d("QuizME", navController.toString());
                        } else {
                            Log.e("QuizME", "NavController is null");
                        }
                    } else {
                        iniciarQuiz();
                    }
                }
            });
        }catch(NullPointerException nullPointerException){
            Log.e("QuizME", nullPointerException.getMessage());
        }

    }

    public void verificadorDeDesbloqueio(String email, int conteudoId, int dificuldadeId){
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call< Integer > metodoBuscarProgressoConteudo = conteudoWS.buscarProgressoConteudo(email, conteudoId);

        metodoBuscarProgressoConteudo.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() >= 3){
                    if(!(conteudoId >= 20)){
                        finalizarConteudoWS("estudante@gmail.com", (conteudoId + 1));
                        Log.d("QuizMe", "Porra meu");
                    }else {
                        Log.d("QuizMe", "caralho");
                    }
                }else if(response.body() <= dificuldadeId){
                    desbloquearQuiz("estudante@gmail.com", conteudoId);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("QuizME", t.getMessage());
            }
        });
    }

    private void desbloquearQuiz(String email, int conteudoId){
        RetrofitConfig config = new RetrofitConfig();
        AtividadeWS atividadeWS = config.getAtividadeWS();
        Call<Boolean> metodoDesbloquearQuiz = atividadeWS.desbloquearQuiz(email, conteudoId);

        metodoDesbloquearQuiz.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("deu erro", "deu bom");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private void alternativaClickListener(){
        escolhaA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarBotao(v, 0);
            }
        });
        escolhaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarBotao(v, 1);
            }
        });
        escolhaC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarBotao(v, 2);
            }
        });
        escolhaD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarBotao(v, 3);
            }
        });
    }
    public void selecionarBotao(View v, int index){
        try{
            respostaEscolhida = alternativas.get(index).isRespostaCerta();
            mudarCorOnClick(v);
            botaoClicado = v;
        }catch(IndexOutOfBoundsException indexOutOfBoundsException){
            Log.e("QuizME", indexOutOfBoundsException.getMessage());
        }
    }

    public void mudarCorOnClick(View view){
        int pL = escolhaC.getPaddingLeft();
        int pT = escolhaC.getPaddingTop();
        int pR = escolhaC.getPaddingRight();
        int pB = escolhaC.getPaddingBottom();

        if(quantidadeAlternativa != 0){
            if(quantidadeAlternativa <= 2){
                escolhas.setPadding(0, 0, 0, 0);

                if(conteudo!=null){
                    if(conteudo.getDisciplina().getId() == 1){
                        escolhaA.setBackgroundResource(R.drawable.btn_verdadeiro_port);
                    }else if(conteudo.getDisciplina().getId() == 2){
                        escolhaA.setBackgroundResource(R.drawable.btn_verdadeiro_mat);
                    }
                }else{
                    escolhaA.setBackgroundResource(R.drawable.btn_verdadeiro_mat);
                }

                escolhaA.setPadding(pL, (int)Math.round(pT*1.18), pR, (int)Math.round(pB*1.18));
                escolhaB.setBackgroundResource(R.drawable.btn_falso);
                escolhaB.setPadding(pL, (int)Math.round(pT*1.18), pR, (int)Math.round(pB*1.18));
                escolhaC.setBackgroundResource(R.drawable.borda);
                escolhaC.setPadding(pL, pT, pR, pB);
                escolhaD.setBackgroundResource(R.drawable.borda);
                escolhaD.setPadding(pL, pT, pR, pB);

                if(conteudo.getDisciplina().getId() == 1){
                    if(view == escolhaA){
                        view.setBackgroundResource(R.drawable.btn_verdadeiro_click_port);
                    }else if(view == escolhaB){
                        view.setBackgroundResource(R.drawable.btn_falso_click_port);
                    }else{
                        view.setBackgroundResource(R.drawable.borda_port);
                    }
                }else if(conteudo.getDisciplina().getId() == 2){
                    if(view == escolhaA){
                        view.setBackgroundResource(R.drawable.btn_verdadeiro_click_mat);
                    }else if(view == escolhaB){
                        view.setBackgroundResource(R.drawable.btn_falso_click_mat);
                    }else{
                        view.setBackgroundResource(R.drawable.borda_mat);
                    }
                }
                view.setPadding(pL, (int)Math.round(pT*1.18), pR, (int)Math.round(pB*1.18));
            }else{
                escolhaA.setBackgroundResource(R.drawable.borda);
                escolhaA.setPadding(pL, pT, pR, pB);
                escolhaB.setBackgroundResource(R.drawable.borda);
                escolhaB.setPadding(pL, pT, pR, pB);
                escolhaC.setBackgroundResource(R.drawable.borda);
                escolhaC.setPadding(pL, pT, pR, pB);
                escolhaD.setBackgroundResource(R.drawable.borda);
                escolhaD.setPadding(pL, pT, pR, pB);

                if(conteudo.getDisciplina().getId() == 1){
                    view.setBackgroundResource(R.drawable.borda_port);
                }else if(conteudo.getDisciplina().getId() == 2){
                    view.setBackgroundResource(R.drawable.borda_mat);
                }
                view.setPadding(pL, pT, pR, pB);
            }
        }
    }

    public void resetarBotoes(){

        int pL = escolhaA.getPaddingLeft();
        int pT = escolhaA.getPaddingTop();
        int pR = escolhaA.getPaddingRight();
        int pB = escolhaA.getPaddingBottom();

        int mL = escolhas.getPaddingLeft();
        int mT = escolhas.getPaddingTop();
        int mR = escolhas.getPaddingRight();
        int mB = escolhas.getPaddingBottom();

        escolhas.setPadding(mL, mT, mR, mB);

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

    public void resetarBotoesVF(){
        int pL = escolhaA.getPaddingLeft();
        int pT = escolhaA.getPaddingTop();
        int pR = escolhaA.getPaddingRight();
        int pB = escolhaA.getPaddingBottom();

        escolhas.setPadding(0, 0, 0, 0);

        if(conteudo!=null){
            if(conteudo.getDisciplina().getId() == 1){
                escolhaA.setBackgroundResource(R.drawable.btn_verdadeiro_port);
            }else if(conteudo.getDisciplina().getId() == 2){
                escolhaA.setBackgroundResource(R.drawable.btn_verdadeiro_mat);
            }
        }else{
            escolhaA.setBackgroundResource(R.drawable.btn_verdadeiro_mat);
        }

        escolhaA.setPadding(pL, (int)Math.round(pT*1.18), pR, (int)Math.round(pB*1.18));
        escolhaB.setBackgroundResource(R.drawable.btn_falso);
        escolhaB.setPadding(pL, (int)Math.round(pT*1.18), pR, (int)Math.round(pB*1.18));
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
                try {
                    String textoDaQuestao = response.body().get(questaoAtual).getEnunciado();
                    tvTextoQuestao.setText(textoDaQuestao);
                    preencherAlternativas(response.body().get(questaoAtual).getId());
                }catch(IndexOutOfBoundsException indexOutOfBoundsException){
                    Log.e("QuizME", indexOutOfBoundsException.getMessage());
                }
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

                quantidadeAlternativa = response.body().size();
                int quantidadeAlternativaTemp = response.body().size();

                try {
                    tvRespostaA.setText(response.body().get(0).getTexto());
                    tvRespostaB.setText(response.body().get(1).getTexto());
                    if(quantidadeAlternativaTemp <=2){
                        deixarInv();
                    }else{
                        deixarVis();
                        tvRespostaC.setText(response.body().get(2).getTexto());
                        tvRespostaD.setText(response.body().get(3).getTexto());
                    }

                    carregandoDialog.removerDialog();
                }catch (IndexOutOfBoundsException indexOutOfBoundsException){

                    Log.e("QuizMe", indexOutOfBoundsException.getMessage());

                }

            }

            @Override
            public void onFailure(Call<List<Alternativa>> call, Throwable t) {
                Log.e("QuizME", t.getMessage());
            }
            public void deixarInv(){
                escolhaC.setVisibility(View.GONE);
                escolhaD.setVisibility(View.GONE);
                tvRespostaA.setVisibility(View.GONE);
                tvRespostaB.setVisibility(View.GONE);
                tvRespostaC.setVisibility(View.GONE);
                tvRespostaD.setVisibility(View.GONE);
                icRespostaA.setVisibility(View.GONE);
                icRespostaB.setVisibility(View.GONE);
                resetarBotoesVF();
            }
            public void deixarVis(){
                escolhaC.setVisibility(View.VISIBLE);
                escolhaD.setVisibility(View.VISIBLE);
                tvRespostaA.setVisibility(View.VISIBLE);
                tvRespostaB.setVisibility(View.VISIBLE);
                tvRespostaC.setVisibility(View.VISIBLE);
                tvRespostaD.setVisibility(View.VISIBLE);
                icRespostaA.setVisibility(View.VISIBLE);
                icRespostaB.setVisibility(View.VISIBLE);
                resetarBotoes();
            }
        });
    }
    public void personalizarTela(int id) {
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Conteudo> metodoBuscar = conteudoWS.buscar(id);

        metodoBuscar.enqueue(new Callback<Conteudo>() {
            @Override
            public void onResponse(Call<Conteudo> call, Response<Conteudo> response) {
                Conteudo tempConteudo = response.body();
                conteudo = tempConteudo;
                try {
                    if(tempConteudo.getDisciplina().getId() == 1){
                        tvNivel.setBackgroundResource(R.drawable.borda_redonda_port);
                        btnFinalizarConteudo.setBackgroundColor(getResources().getColor(R.color.istudy_roxo));
                    }else if (tempConteudo.getDisciplina().getId() == 2){
                        tvNivel.setBackgroundResource(R.drawable.borda_redonda_mat);
                        btnFinalizarConteudo.setBackgroundColor(getResources().getColor(R.color.istudy_verde));
                    }
                }catch(NullPointerException nullPointerException){
                    Log.e("QuizME", nullPointerException.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Conteudo> call, Throwable t) {

            }
        });
    }
    public void finalizarConteudoWS(String email, int conteudoId) {
        RequestConteudo requestConteudo = new RequestConteudo(email, conteudoId);

        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Boolean> finalizar = conteudoWS.finalizar(requestConteudo);

        finalizar.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.e("QuizME", response.body()+"");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("QuizME", t.getMessage());
            }
        });
    }

}
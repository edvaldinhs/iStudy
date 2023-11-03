package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterQuiz;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnQuizClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quizzes extends Fragment implements OnQuizClickListener {

    private RecyclerView rvQuizzes;

    //Armazena as quizzes
    private List<Conteudo> quizzes = new ArrayList<>();

    private NavController navController;

    private Bundle extras;

    private TextView tvNomeDisciplina;

    private int id;

    private String nomeDisciplina;

    private EditText etProcurarQuiz;

    AdapterQuiz adapterQuizzes;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public Quizzes() {
    }

    public static Quizzes newInstance(String param1, String param2) {
        Quizzes fragment = new Quizzes();
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
        View view = inflater.inflate(R.layout.fragment_quizzes, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        etProcurarQuiz = view.findViewById(R.id.etProcurarQuizzes);
        String searchResultado = etProcurarQuiz.getText().toString();

        rvQuizzes = view.findViewById(R.id.recyclerViewQuizzes);

        rvQuizzes.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        tvNomeDisciplina = view.findViewById(R.id.tvDisciplinas);

        extras = getArguments();

        if(extras != null){
            id = extras.getInt("disciplinaId");
            setarNomeDisciplinasPeloID(id);
            preencherQuizzes(id, searchResultado);
        }

        etProcurarQuiz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchResultado = charSequence.toString();
                preencherQuizzes(id, searchResultado);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        
        return view;
    }
    
    private void listarQuizzes(){
        adapterQuizzes = new AdapterQuiz(getActivity(), quizzes, this);
        rvQuizzes.setAdapter(adapterQuizzes);
    }
    private void preencherQuizzes(int id, String pesquisa){
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<List<Conteudo>> metodoListar = conteudoWS.listarTodos();

        quizzes.clear();

        metodoListar.enqueue(new Callback<List<Conteudo>>() {
            @Override
            public void onResponse(Call<List<Conteudo>> call, Response<List<Conteudo>> response) {
                if (response.body() != null) {
                    if(adapterQuizzes != null){
                        adapterQuizzes.clearData();
                    }
                    for (Conteudo conteudo : response.body()) {
                        if (conteudo.getDisciplina().getId() == id) {
                            if (pesquisa.isEmpty() || conteudo.getNome().toLowerCase().contains(pesquisa.toLowerCase())) {
                                quizzes.add(conteudo);
                            }
                        }
                    }
                    listarQuizzes();
                }
            }

            @Override
            public void onFailure(Call<List<Conteudo>> call, Throwable t) {
            }
        });
    }
    public void setarNomeDisciplinasPeloID(int id) {
        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<Disciplina> metodoBuscar = disciplinaWS.buscar(id);

        metodoBuscar.enqueue(new Callback<Disciplina>() {
            @Override
            public void onResponse(Call<Disciplina> call, Response<Disciplina> response) {
                Disciplina disciplina = response.body();
                tvNomeDisciplina.setText(disciplina.getNome());
            }

            @Override
            public void onFailure(Call<Disciplina> call, Throwable t) {
                Log.e("pedro", t.getMessage());
            }
        });
    }

    @Override
    public void onQuizClick(int id) {
        Bundle cartinha = new Bundle();

        cartinha.putInt("quizId", id);

        if (navController != null) {
            navController.navigate(R.id.action_quiz_to_atividades, cartinha);
            quizzes.clear();
            Log.e("QuizzesFragment", navController.toString());
        } else {
            Log.e("QuizzesFragment", "NavController is null");
        }
    }
}
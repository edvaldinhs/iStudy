package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous.AvisoDialog;
import br.edu.ifrn.sc.info.istudy.ViewModel.SharedViewModel;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterQuizzesByDisciplinas;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnQuizzesByDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzesByDisciplina extends Fragment implements OnQuizzesByDisciplinaClickListener {

    //Cria o RecyclerView para os cards das disciplinas
    private RecyclerView rvQuizzesByDisciplina;

    //Armazena as disciplinas
    private List<Disciplina> disciplinas = new ArrayList<>();

    private String email;
    private SharedViewModel sharedViewModel;

    private Bundle extras;

    private NavController navController;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public QuizzesByDisciplina() {

    }

    public static QuizzesByDisciplina newInstance(String param1, String param2) {
        QuizzesByDisciplina fragment = new QuizzesByDisciplina();
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
        View view = inflater.inflate(R.layout.fragment_quizzes_by_discipliana, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getSharedBundle().observe(this, bundle -> {
            extras = bundle;
            email = extras.getString("email");
        });

        //Diz quem é e de onde veio o RecyclerView
        rvQuizzesByDisciplina = view.findViewById(R.id.rvQuizzesByDisciplina);

        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        //Usa uma classe chamada LinearLayoutManager pra organizar os cards de disciplina
        rvQuizzesByDisciplina.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        preencherDisciplinas();

        return view;
    }

    private void listarQuizzes(){
        AdapterQuizzesByDisciplinas adapterQuizzesByDisciplinas = new AdapterQuizzesByDisciplinas(getActivity(), disciplinas, this, navController);
        rvQuizzesByDisciplina.setAdapter(adapterQuizzesByDisciplinas);
    }

    //Usando o Web service pra preencher a lista de disciplinas
    private void preencherDisciplinas(){
        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<List<Disciplina>> metodoListar = disciplinaWS.listarTodas();

        //Deixa o botão voltar visível
        getActivity().findViewById(R.id.voltar).setVisibility(View.VISIBLE);

        metodoListar.enqueue(new Callback<List<Disciplina>>() {
            @Override
            public void onResponse(Call<List<Disciplina>> call, Response<List<Disciplina>> response) {
                disciplinas = response.body();
                listarQuizzes();
            }

            @Override
            public void onFailure(Call<List<Disciplina>> call, Throwable t) {
                try {
                    new AvisoDialog(getActivity(), "Não foi possível carregar as disciplinas").iniciarAvisoDialog();
                }catch(NullPointerException nullPointerException){
                    new AvisoDialog(getActivity(), "Não foi possível carregar as disciplinas").iniciarAvisoDialog();
                    Log.d("ErroPreencher",nullPointerException.getMessage());
                }

            }
        });
    }

    @Override
    public void onQuizzesByDisciplinaClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("disciplinaId", id);
        bundle.putString("email", email);

        if (navController != null) {
            navController.navigate(R.id.action_estudos_to_quiz, bundle);
            Log.e("QuizzesFragment", navController.toString());
        } else {
            Log.e("QuizzesFragment", "NavController is null");
        }
    }
}
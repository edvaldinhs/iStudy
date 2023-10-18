package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterConteudos;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnQuizzesByDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragConteudo extends Fragment implements OnQuizzesByDisciplinaClickListener {

    //Cria o RecyclerView para os cards dos conteudos
    private RecyclerView rvConteudo;

    //Armazena as conteudos
    private List<Conteudo> conteudos = new ArrayList<>();

    private NavController navController;

    private Bundle extras;

    TextView tvNomeDisciplina;

    private int id;

    private String nomeDisciplina;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragConteudo() {

    }

    public static FragConteudo newInstance(String param1, String param2) {
        FragConteudo fragment = new FragConteudo();
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
        View view = inflater.inflate(R.layout.fragment_conteudo, container, false);

        tvNomeDisciplina = view.findViewById(R.id.tvDisciplinas);

        extras = getArguments();

        if(extras != null){
            id = extras.getInt("disciplinaId");

            setarNomeDisciplinasPeloID(id);
        }

        getActivity().findViewById(R.id.voltar).setVisibility(View.VISIBLE);



        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        rvConteudo = view.findViewById(R.id.recyclerViewConteudo);

        rvConteudo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        preencherConteudos();

        return view;
    }

    //Método Pra listar os cards de Conteudos
    private void listarConteudos(){
        AdapterConteudos adapterConteudos = new AdapterConteudos(getActivity(), conteudos, navController);
        rvConteudo.setAdapter(adapterConteudos);
    }

    private void preencherConteudos(){
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<List<Conteudo>> metodoListar = conteudoWS.listarTodos();

        metodoListar.enqueue(new Callback<List<Conteudo>>() {
            @Override
            public void onResponse(Call<List<Conteudo>> call, Response<List<Conteudo>> response) {
                conteudos = response.body();
                listarConteudos();
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
    public void onQuizzesByDisciplinaClick(int id) {

    }
}
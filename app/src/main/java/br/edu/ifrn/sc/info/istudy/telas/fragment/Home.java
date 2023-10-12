package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterDisciplinas;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.telas.TelaInicial;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {

    //Cria o RecyclerView para os cards das disciplinas
    RecyclerView rvDisciplina;

    //Armazena as disciplinas
    List<Disciplina> disciplinas = new ArrayList<>();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Home() {

    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

    //Quando criar a tela e seus componentes, esse código roda
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Diz quem é e de onde veio o RecyclerView
        rvDisciplina = view.findViewById(R.id.recyclerview_Disciplinas);

        //Usa uma classe chamada LinearLayoutManager pra organizar os cards de disciplina
        rvDisciplina.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //Testes de Funcionamento
//        Disciplina disciplina = new Disciplina(1,"Teste");
//        Disciplina disciplina2 = new Disciplina(2,"Matemautica");
//        disciplinas.add(disciplina);
//        disciplinas.add(disciplina2);


        listarDisciplinas();
        return view;
    }

    //Método Pra listar os cards de Disciplinas
    private void listarDisciplinas(){
        preencherDisciplinas();
        AdapterDisciplinas adapterDisciplinas = new AdapterDisciplinas(getActivity(), disciplinas);
        rvDisciplina.setAdapter(adapterDisciplinas);
    }

    //Usando o Web service pra preencher a lista de disciplinas
    private void preencherDisciplinas(){
        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<List<Disciplina>> metodoListar = disciplinaWS.listarTodas();

        metodoListar.enqueue(new Callback<List<Disciplina>>() {
            @Override
            public void onResponse(Call<List<Disciplina>> call, Response<List<Disciplina>> response) {
                disciplinas = response.body();
            }

            @Override
            public void onFailure(Call<List<Disciplina>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
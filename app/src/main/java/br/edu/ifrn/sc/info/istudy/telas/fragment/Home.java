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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterDisciplinas;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.telas.TelaInicial;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment implements OnDisciplinaClickListener {

    //Cria o RecyclerView para os cards das disciplinas
    RecyclerView rvDisciplina;

    //Armazena as disciplinas
    List<Disciplina> disciplinas = new ArrayList<>();

    NavController navController;


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

        //Deixa o botão voltar invisível na home
        getActivity().findViewById(R.id.voltar).setVisibility(View.INVISIBLE);

        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        //Diz quem é e de onde veio o RecyclerView
        rvDisciplina = view.findViewById(R.id.recyclerview_Disciplinas);

        //Usa uma classe chamada LinearLayoutManager pra organizar os cards de disciplina
        rvDisciplina.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        preencherDisciplinas();
        return view;
    }

    //Método Pra listar os cards de Disciplinas
    private void listarDisciplinas(){
        AdapterDisciplinas adapterDisciplinas = new AdapterDisciplinas(getActivity(), disciplinas, this, navController);
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
                listarDisciplinas();
            }

            @Override
            public void onFailure(Call<List<Disciplina>> call, Throwable t) {
                try {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }catch(NullPointerException nullPointerException){
                    Log.e("ErroPreencher",nullPointerException.getMessage());
                }
            }
        });
    }

    @Override
    public void onDisciplinaClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("disciplinaId", id);

        FragConteudo fragConteudo = new FragConteudo();
        fragConteudo.setArguments(bundle);

        if (navController != null) {
            navController.navigate(R.id.action_home_to_conteudo);
            Log.e("HomeFragment", "To clicando");
        } else {
            Log.e("HomeFragment", "NavController is null");
        }
    }
}
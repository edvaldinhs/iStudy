package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import br.edu.ifrn.sc.info.istudy.adapters.AdapterConteudos;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConteudoClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragConteudo extends Fragment implements OnConteudoClickListener {

    //Cria o RecyclerView para os cards dos conteudos
    private RecyclerView rvConteudo;

    //Armazena as conteudos
    private List<Conteudo> conteudos = new ArrayList<>();

    private String email;

    private NavController navController;

    private Bundle extras;

    private TextView tvNomeDisciplina;

    private int id;

    private String nomeDisciplina;

    private EditText etProcurarConteudo;

    private AdapterConteudos adapterConteudos;

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

        getActivity().findViewById(R.id.voltar).setVisibility(View.VISIBLE);

        etProcurarConteudo = view.findViewById(R.id.etProcurarConteudo);
        String searchResultado = etProcurarConteudo.getText().toString();

        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        rvConteudo = view.findViewById(R.id.recyclerViewConteudo);

        rvConteudo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        tvNomeDisciplina = view.findViewById(R.id.tvDisciplinas);

        extras = getArguments();

        if(extras != null){
            id = extras.getInt("disciplinaId");
            email = extras.getString("email");
            setarNomeDisciplinasPeloID(id);
            preencherConteudos(id, searchResultado);
        }

        etProcurarConteudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchResultado = charSequence.toString();
                preencherConteudos(id, searchResultado);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }

    //Método pra listar os cards de Conteudos
    private void listarConteudos(){
        adapterConteudos = new AdapterConteudos(getActivity(), getActivity(), conteudos, navController,this, email);
        rvConteudo.setAdapter(adapterConteudos);
    }

    private void preencherConteudos(int id, String pesquisa){
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<List<Conteudo>> metodoListar = conteudoWS.listarTodos();

        conteudos.clear();

        metodoListar.enqueue(new Callback<List<Conteudo>>() {
            @Override
            public void onResponse(Call<List<Conteudo>> call, Response<List<Conteudo>> response) {
                if (response.body() != null) {
                    if(adapterConteudos != null){
                        adapterConteudos.clearData();
                    }
                    for (Conteudo conteudo : response.body()) {
                       try {
                           if (conteudo.getDisciplina().getId() == id) {
                               if (pesquisa.isEmpty() || conteudo.getNome().toLowerCase().contains(pesquisa.toLowerCase())) {
                                   conteudos.add(verificarDesbloquear(email,conteudo));
                                   Log.d("tste", verificarDesbloquear(email,conteudo).getBloqueado()+"");
                               }
                           }
                       }catch ( NullPointerException nullPointerException){
                           Log.e("FragConteudo", nullPointerException.getMessage());

                       }
                    }
                    listarConteudos();
                }
            }

            @Override
            public void onFailure(Call<List<Conteudo>> call, Throwable t) {
            }
        });
    }

    private Conteudo verificarDesbloquear(String email, Conteudo conteudo){
        Conteudo resultConteudo = conteudo;

        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call< Integer > metodoBuscarProgressoConteudo = conteudoWS.buscarProgressoConteudo(email, conteudo.getId());

        metodoBuscarProgressoConteudo.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() != null){
                    if(response.body() >= 1){
                        resultConteudo.conteudoDesbloquear();
                    }
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("ConteudosHolder", t.getMessage());
            }
        });
        return resultConteudo;
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
    public void onConteudoClick(int id) {
        Bundle cartinha = new Bundle();

        extras = getArguments();

        if(extras != null){
            email = extras.getString("email");
        }

        cartinha.putInt("conteudoId", id);
        cartinha.putString("email", email);

        if (navController != null) {
            navController.navigate(R.id.action_conteudo_to_conteudoDescricao, cartinha);
            conteudos.clear();
            Log.e("ConteudoFragment", navController.toString());
        } else {
            Log.e("ConteudoFragment", "NavController is null");
        }
    }
}
package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.ViewModel.SharedViewModel;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterRanking;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterRanking;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.dominio.Icone;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ranking extends Fragment {

    private RecyclerView rvRanking;
    
    private List<Estudante> estudantes = new ArrayList<>();

    private Bundle extras;

    private String email;

    private SharedViewModel sharedViewModel;

    private AdapterRanking adapterRanking;

    private List<Icone> icones = new ArrayList<>();

    private ImageView fotoPrimeiroLugar;
    private TextView nomePrimeiroLugar;

    private ImageView fotoSegundoLugar;
    private TextView nomeSegundoLugar;

    private ImageView fotoTerceiroLugar;
    private TextView nomeTerceiroLugar;

    
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Ranking() {
    }

    public static Ranking newInstance(String param1, String param2) {
        Ranking fragment = new Ranking();
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
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        getActivity().findViewById(R.id.voltar).setVisibility(View.VISIBLE);
        preencherIcones();

        rvRanking = view.findViewById(R.id.recyclerViewRanking);

        rvRanking.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getSharedBundle().observe(this, bundle -> {
            extras = bundle;
            email = extras.getString("email");
        });

        fotoPrimeiroLugar = view.findViewById(R.id.ivFotoAluno);
        nomePrimeiroLugar = view.findViewById(R.id.tvAlunoEmPrimeiro);

        fotoSegundoLugar = view.findViewById(R.id.ivFotoAluno2);
        nomeSegundoLugar = view.findViewById(R.id.tvAlunoEmSegundo);

        fotoTerceiroLugar = view.findViewById(R.id.ivFotoAluno3);
        nomeTerceiroLugar = view.findViewById(R.id.tvAlunoEmTerceiro);

        preencherRanking();

        return view;
    }
    private void listarRanking(){
        adapterRanking = new AdapterRanking(getActivity(), estudantes, icones);
        rvRanking.setAdapter(adapterRanking);
    }

    private void preencherRanking(){
        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<List<Estudante>> metodoListar = estudanteWS.listarRanking();

        metodoListar.enqueue(new Callback<List<Estudante>>() {
            @Override
            public void onResponse(Call<List<Estudante>> call, Response<List<Estudante>> response) {
                if (response.body() != null) {
                    if(adapterRanking != null){
                        adapterRanking.clearData();
                    }
                    estudantes = response.body();
                        for (Icone icone : icones){
                            if(estudantes.size() >= 1){
                                if (estudantes.get(0).getFoto() != null && estudantes.get(0).getFoto().equals(icone.getId())){
                                    fotoPrimeiroLugar.setImageDrawable(icone.getIcone());
                                }
                            }
                            if(estudantes.size() >= 2){
                                if (estudantes.get(1).getFoto() != null && estudantes.get(1).getFoto().equals(icone.getId())){
                                    fotoSegundoLugar.setImageDrawable(icone.getIcone());
                                }
                            }
                            if(estudantes.size() >= 3){
                                if (estudantes.get(2).getFoto() != null && estudantes.get(2).getFoto().equals(icone.getId())){
                                    fotoTerceiroLugar.setImageDrawable(icone.getIcone());
                                }
                            }
                        }
                    if(estudantes.size() >= 1){
                        nomePrimeiroLugar.setText(estudantes.get(0).getNome());
                    }
                    if(estudantes.size() >= 2){
                        nomeSegundoLugar.setText(estudantes.get(1).getNome());
                    }
                    if(estudantes.size() >= 3) {
                        nomeTerceiroLugar.setText(estudantes.get(2).getNome());
                    }
                    listarRanking();
                }
            }

            @Override
            public void onFailure(Call<List<Estudante>> call, Throwable t) {

            }
        });
    }

    public void preencherIcones(){
        icones.add(new Icone("begging", getResources().getDrawable(R.drawable.begging)));
        icones.add(new Icone("carinha", getResources().getDrawable(R.drawable.carinha)));
        icones.add(new Icone("cat", getResources().getDrawable(R.drawable.cat)));
        icones.add(new Icone("cry", getResources().getDrawable(R.drawable.cry)));
        icones.add(new Icone("destroyed", getResources().getDrawable(R.drawable.destroyed)));
        icones.add(new Icone("devil", getResources().getDrawable(R.drawable.devil)));
        icones.add(new Icone("happy", getResources().getDrawable(R.drawable.happy)));
        icones.add(new Icone("magic", getResources().getDrawable(R.drawable.magic)));
        icones.add(new Icone("mask", getResources().getDrawable(R.drawable.mask)));
        icones.add(new Icone("melted", getResources().getDrawable(R.drawable.melted)));
        icones.add(new Icone("sad", getResources().getDrawable(R.drawable.sad)));
        icones.add(new Icone("sad_scream", getResources().getDrawable(R.drawable.sad_scream)));
        icones.add(new Icone("shy", getResources().getDrawable(R.drawable.shy)));
        icones.add(new Icone("snail", getResources().getDrawable(R.drawable.snail)));
        icones.add(new Icone("turtle", getResources().getDrawable(R.drawable.turtle)));
        icones.add(new Icone("weird", getResources().getDrawable(R.drawable.weird)));
        icones.add(new Icone("whatever", getResources().getDrawable(R.drawable.whatever)));
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            preencherRanking();
        }
    }

}
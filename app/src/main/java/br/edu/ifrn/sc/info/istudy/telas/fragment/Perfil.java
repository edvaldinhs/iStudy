package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterConquistas;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterQuiz;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;

public class Perfil extends Fragment {

    private RecyclerView rvConquista;

    private AdapterConquistas adapterConquistas;
    private List<Conquista> conquistas = new ArrayList<>();
    private int progressoAtual = 0;
    private ProgressBar progresso;

    private TextView tvPontos;

    private CardView editarFoto;
    private ImageView fotoAluno;

    private ActivityResultLauncher<Intent> resultadoDaProcura;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Perfil() {

    }

    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

//        preencherLauncherDeResultado();

        //Deixa o botão voltar visível
        getActivity().findViewById(R.id.voltar).setVisibility(View.VISIBLE);

        tvPontos = view.findViewById(R.id.tvPontuacao);
        progresso = view.findViewById(R.id.pgBarraPontuacao);

        progressoAtual = 50;

        tvPontos.setText(progressoAtual+"/100 pontos");
        progresso.setProgress(progressoAtual);
        progresso.setMax(100);

        rvConquista = view.findViewById(R.id.rvConquistas);

        rvConquista.setLayoutManager(new GridLayoutManager(getActivity(), 3
        ));

        editarFoto = view.findViewById(R.id.cvEditarFoto);
        fotoAluno = view.findViewById(R.id.ivFotoAluno);

        editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent procurarFoto = new Intent(Intent.ACTION_PICK);
//                procurarFoto.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                resultadoDaProcura.launch(procurarFoto);



            }
        });

        conquistas.add(new Conquista(1,"","primeiro","","2023-10-10"));
        conquistas.add(new Conquista(2,"","segundo","","2023-10-10"));
        conquistas.add(new Conquista(3,"","terceiro","","2023-10-10"));
        listarConquistas();

        return view;
    }
    public void preencherLauncherDeResultado(){
        resultadoDaProcura = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            fotoAluno.setImageURI(data.getData());
                        }
                    }
                });
    }
    private void listarConquistas(){
        adapterConquistas = new AdapterConquistas(getActivity(), conquistas);
        rvConquista.setAdapter(adapterConquistas);
    }
}
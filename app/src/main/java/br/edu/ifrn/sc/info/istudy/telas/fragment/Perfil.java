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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous.EscolherFotoDialog;
import br.edu.ifrn.sc.info.istudy.ViewModel.SharedViewModel;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterConquistas;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConquistaClickListener;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConteudoClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.dominio.Icone;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.telas.TelaInicial;
import br.edu.ifrn.sc.info.istudy.telas.TelaPrincipal;
import br.edu.ifrn.sc.info.istudy.ws.ConquistaWS;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends Fragment implements OnConquistaClickListener {

    private RecyclerView rvConquista;

    private AdapterConquistas adapterConquistas;
    private List<Conquista> conquistas = new ArrayList<>();
    private List<Icone> icones = new ArrayList<>();
    private int progressoAtual = 0;
    private ProgressBar progresso;

    private TextView tvTitulo;

    private Bundle extras;
    private String email;
    private Estudante estudante;

    private TextView tvPontos;

    private CardView editarFoto;
    private ImageView fotoAluno;

    private ActivityResultLauncher<Intent> resultadoDaProcura;
    private SharedViewModel sharedViewModel;

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

        icones = new ArrayList<>();
        preencherIcones();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observe changes to the shared bundle
        sharedViewModel.getSharedBundle().observe(this, bundle -> {
            extras = bundle;
            email = extras.getString("email");
            preencherDados(email);
        });

//        preencherLauncherDeResultado();

        //Deixa o botão voltar visível
        getActivity().findViewById(R.id.voltar).setVisibility(View.VISIBLE);

        tvPontos = view.findViewById(R.id.tvPontuacao);
        progresso = view.findViewById(R.id.pgBarraPontuacao);

        rvConquista = view.findViewById(R.id.rvConquistas);

        rvConquista.setLayoutManager(new GridLayoutManager(getActivity(), 3
        ));
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filenameToDelete = "usuario.json";
                boolean deleted = getContext().deleteFile(filenameToDelete);
                if(deleted){
                    restartApp();
                }else{
                    Toast.makeText(getActivity(), "Não foi possível deslogar :(", Toast.LENGTH_LONG).show();
                }
            }
        });

        editarFoto = view.findViewById(R.id.cvEditarFoto);
        fotoAluno = view.findViewById(R.id.ivFotoAluno);

        editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent procurarFoto = new Intent(Intent.ACTION_PICK);
//                procurarFoto.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                resultadoDaProcura.launch(procurarFoto);

                EscolherFotoDialog escolherFotoDialog = new EscolherFotoDialog(getActivity(), view, icones, estudante);
                escolherFotoDialog.iniciarEscolherFotoDialog();

//                String imageUrl = "https://drive.google.com/uc?export=download&id=1IAYE6gt1CLS7-Jg8bA0BeadkNhgMt5gY";
//                Picasso.get().load(imageUrl).into(fotoAluno);

            }
        });

        preencherConquistas();

        return view;
    }
    public void restartApp() {
        Intent intent = new Intent(getActivity(), TelaInicial.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        getActivity().finish();
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
        adapterConquistas = new AdapterConquistas(getActivity(), requireActivity(), conquistas, this, email);
        rvConquista.setAdapter(adapterConquistas);
    }
    private void preencherConquistas(){
        RetrofitConfig config = new RetrofitConfig();
        ConquistaWS conquistaWS = config.getConquistaWS();
        Call<List<Conquista>> metodoListar = conquistaWS.listarTodas();

        metodoListar.enqueue(new Callback<List<Conquista>>() {
            @Override
            public void onResponse(Call<List<Conquista>> call, Response<List<Conquista>> response) {
                conquistas = response.body();
                listarConquistas();
            }

            @Override
            public void onFailure(Call<List<Conquista>> call, Throwable t) {

            }
        });

    }

    public void preencherIcones(){
        icones.add(new Icone("begging", getActivity().getResources().getDrawable(R.drawable.begging)));
        icones.add(new Icone("carinha", getActivity().getResources().getDrawable(R.drawable.carinha)));
        icones.add(new Icone("cat", getActivity().getResources().getDrawable(R.drawable.cat)));
        icones.add(new Icone("cry", getActivity().getResources().getDrawable(R.drawable.cry)));
        icones.add(new Icone("destroyed", getActivity().getResources().getDrawable(R.drawable.destroyed)));
        icones.add(new Icone("devil", getActivity().getResources().getDrawable(R.drawable.devil)));
        icones.add(new Icone("happy", getActivity().getResources().getDrawable(R.drawable.happy)));
        icones.add(new Icone("magic", getActivity().getResources().getDrawable(R.drawable.magic)));
        icones.add(new Icone("mask", getActivity().getResources().getDrawable(R.drawable.mask)));
        icones.add(new Icone("melted", getActivity().getResources().getDrawable(R.drawable.melted)));
        icones.add(new Icone("sad", getActivity().getResources().getDrawable(R.drawable.sad)));
        icones.add(new Icone("sad_scream", getActivity().getResources().getDrawable(R.drawable.sad_scream)));
        icones.add(new Icone("shy", getActivity().getResources().getDrawable(R.drawable.shy)));
        icones.add(new Icone("snail", getActivity().getResources().getDrawable(R.drawable.snail)));
        icones.add(new Icone("turtle", getActivity().getResources().getDrawable(R.drawable.turtle)));
        icones.add(new Icone("weird", getActivity().getResources().getDrawable(R.drawable.weird)));
        icones.add(new Icone("whatever", getActivity().getResources().getDrawable(R.drawable.whatever)));
    }

    private void preencherDados(String email){
        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<Estudante> metodoBuscar = estudanteWS.buscar(email);

        metodoBuscar.enqueue(new Callback<Estudante>() {
            @Override
            public void onResponse(Call<Estudante> call, Response<Estudante> response) {
                estudante = response.body();
                try {
                    progressoAtual = estudante.getPontuacao();
                    tvPontos.setText(progressoAtual+"/100 pontos");
                    progresso.setProgress(progressoAtual);
                    progresso.setMax(100);

                    tvTitulo.setText(estudante.getNome());

                    String estudanteFoto = estudante.getFoto();
                    for (Icone icone : icones){
                        if (estudanteFoto != null && estudanteFoto.equals(icone.getId())){
                            fotoAluno.setImageDrawable(icone.getIcone());
                        }
                    }
                }catch(NullPointerException nullPointerException){
                    Log.e("Perfil", nullPointerException.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Estudante> call, Throwable t) {
                Log.e("Perfil", t.getMessage());
            }
        });
    }

    @Override
    public void onConquistaClick(Conquista conquista) {

    }
}
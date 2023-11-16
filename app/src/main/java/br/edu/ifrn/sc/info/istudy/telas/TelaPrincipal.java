package br.edu.ifrn.sc.info.istudy.telas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.ViewModel.SharedViewModel;
import br.edu.ifrn.sc.info.istudy.databinding.ActivityTelaInicialBinding;
import br.edu.ifrn.sc.info.istudy.databinding.ActivityTelaPrincipalBinding;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.dominio.Icone;
import br.edu.ifrn.sc.info.istudy.gerenciadorDeArquivo.SecureStorageHelper;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TelaPrincipal extends AppCompatActivity {

    private ImageView voltar;
    private TextView iStudyLogo;
    private ActivityTelaPrincipalBinding binding;

    private NavHostFragment navHostFragment;
    private NavController navController;

    private Bundle bundle = new Bundle();
    private Bundle extras = new Bundle();

    private SharedViewModel sharedViewModel;
    
    private List<Icone> icones = new ArrayList<>();
    
    private Estudante estudante;

    private ImageView fotoAluno;
    private TextView tvPontos;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityTelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        extras = getIntent().getExtras();

        preencherIcones();

        fotoAluno = findViewById(R.id.ivFotoAluno);
        tvPontos = findViewById(R.id.tvPontos);

        if(extras != null){
            bundle.putString("email", extras.getString("email"));
            preencherDados(extras.getString("email"));
        }

        initNavigation();


        iStudyLogo = findViewById(R.id.iStudyLogo);
        voltar = findViewById(R.id.voltar);

        String i = getColoredSpanned("i", "#7EBA2F");
        String study = getColoredSpanned("Study","#A547F9");

        iStudyLogo.setText(Html.fromHtml(i+study));


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    private void initNavigation(){
        navHostFragment
                = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        navController = navHostFragment.getNavController();
        navController.setGraph(R.navigation.nav_principal, bundle);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        // Pass the bundle to all destinations
        sharedViewModel.setSharedBundle(bundle);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
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

    public void preencherDados(String email){
        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<Estudante> metodoBuscar = estudanteWS.buscar(email);

        metodoBuscar.enqueue(new Callback<Estudante>() {
            @Override
            public void onResponse(Call<Estudante> call, Response<Estudante> response) {
                estudante = response.body();
                try {
                    int initialPoints = Integer.parseInt(tvPontos.getText().toString());
                    int finalPoints = estudante.getPontuacao();

                    ValueAnimator animator = ValueAnimator.ofInt(initialPoints, finalPoints);
                    animator.setDuration(2000);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());

                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int animatedValue = (int) animation.getAnimatedValue();
                            tvPontos.setText(String.valueOf(animatedValue));
                        }
                    });
                    animator.start();

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
}
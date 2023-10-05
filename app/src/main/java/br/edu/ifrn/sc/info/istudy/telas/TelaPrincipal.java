package br.edu.ifrn.sc.info.istudy.telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.databinding.ActivityTelaInicialBinding;
import br.edu.ifrn.sc.info.istudy.databinding.ActivityTelaPrincipalBinding;


public class TelaPrincipal extends AppCompatActivity {

    private ImageView retorno;
    private TextView iStudyLogo;
    private ActivityTelaPrincipalBinding binding;

    private NavHostFragment navHostFragment;
    private NavController navController;

    private Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityTelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNavigation();

        iStudyLogo = findViewById(R.id.iStudyLogo);
        retorno = findViewById(R.id.voltar);

        String i = getColoredSpanned("i", "#7EBA2F");
        String study = getColoredSpanned("Study","#A547F9");

        iStudyLogo.setText(Html.fromHtml(i+study));

        retorno.setVisibility(View.INVISIBLE);
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
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
    }

}
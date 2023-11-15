package br.edu.ifrn.sc.info.istudy.telas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.ViewModel.SharedViewModel;
import br.edu.ifrn.sc.info.istudy.databinding.ActivityTelaInicialBinding;
import br.edu.ifrn.sc.info.istudy.databinding.ActivityTelaPrincipalBinding;
import br.edu.ifrn.sc.info.istudy.gerenciadorDeArquivo.SecureStorageHelper;


public class TelaPrincipal extends AppCompatActivity {

    private ImageView voltar;
    private TextView iStudyLogo;
    private ActivityTelaPrincipalBinding binding;

    private NavHostFragment navHostFragment;
    private NavController navController;

    private Bundle bundle = new Bundle();
    private Bundle extras = new Bundle();

    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityTelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        extras = getIntent().getExtras();

        if(extras != null){
            bundle.putString("email", extras.getString("email"));
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
}
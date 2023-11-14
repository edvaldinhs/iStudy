package br.edu.ifrn.sc.info.istudy.SheetDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterConquistas;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterIcone;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnIconeClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.dominio.Icone;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscolherFotoDialog implements OnIconeClickListener {

    private Activity activity;
    private AlertDialog dialog;
    private RecyclerView rvIcones;
    private AdapterIcone adapterIcones;
    private List<Icone> icones;
    private ImageView fotoAluno;
    private Estudante estudante;

    private View view;

    public EscolherFotoDialog(Activity activity, View view, List<Icone> icones, Estudante estudante){
        this.activity = activity;
        this.view = view;
        this.icones = icones;
        this.estudante = estudante;
    }

    public void iniciarEscolherFotoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.TransparentAlertDialog);

        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_escolher_icone, null);
        builder.setView(dialogView);
        builder.setCancelable(true);

        rvIcones = dialogView.findViewById(R.id.rvIcone);
        rvIcones.setLayoutManager(new GridLayoutManager(activity, 2));

        fotoAluno = view.findViewById(R.id.ivFotoAluno);

        dialog = builder.create();
        dialog.show();

        preencherIcones();
    }

    public void removerDialog(){
        dialog.dismiss();
    }
    private void preencherIcones(){
        listarIcones();
    }

    private void listarIcones(){
        adapterIcones = new AdapterIcone(activity, icones, this);
        rvIcones.setAdapter(adapterIcones);
    }

    @Override
    public void onIconeClick(int position) {
        Icone selectedIcone = icones.get(position);

        inserirFotoEstudante(estudante, selectedIcone.getId());
        fotoAluno.setImageDrawable(selectedIcone.getIcone());

        removerDialog();
    }

    private void inserirFotoEstudante(Estudante estudante, String nomeIcone){
        Estudante estudante1 = estudante;
        estudante1.setFoto(nomeIcone);
        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<Boolean> metodoAtualizar = estudanteWS.atualizar(estudante1);

        metodoAtualizar.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
}

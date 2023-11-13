package br.edu.ifrn.sc.info.istudy.SheetDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

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
import br.edu.ifrn.sc.info.istudy.dominio.Icone;

public class EscolherFotoDialog implements OnIconeClickListener {

    private Activity activity;
    private AlertDialog dialog;
    private RecyclerView rvIcones;
    private AdapterIcone adapterIcones;
    private List<Icone> icones = new ArrayList<>();

    private View view;

    public EscolherFotoDialog(Activity activity, View view){
        this.activity = activity;
        this.view = view;
    }

    public void iniciarEscolherFotoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.TransparentAlertDialog);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_escolher_icone, null));
        builder.setCancelable(true);

        rvIcones = view.findViewById(R.id.rvIcone);

        rvIcones.setLayoutManager(new GridLayoutManager(activity, 2));

        dialog = builder.create();
        dialog.show();

        preencherIcones();

    }

    public void removerDialog(){
        dialog.dismiss();
    }
    private void preencherIcones(){
        icones.add(new Icone("begging", activity.getResources().getDrawable(R.drawable.begging)));
        icones.add(new Icone("begging", activity.getResources().getDrawable(R.drawable.begging)));
        icones.add(new Icone("begging", activity.getResources().getDrawable(R.drawable.begging)));
        icones.add(new Icone("begging", activity.getResources().getDrawable(R.drawable.begging)));
        icones.add(new Icone("begging", activity.getResources().getDrawable(R.drawable.begging)));
        icones.add(new Icone("begging", activity.getResources().getDrawable(R.drawable.begging)));
        listarConquistas();
    }

    private void listarConquistas(){
        adapterIcones = new AdapterIcone(activity, icones, this);
        rvIcones.setAdapter(adapterIcones);
    }

    @Override
    public void onIconeClick(int id) {

    }
}

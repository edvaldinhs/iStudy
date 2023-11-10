package br.edu.ifrn.sc.info.istudy.SheetDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import br.edu.ifrn.sc.info.istudy.R;

public class CarregandoDialog {

    private Activity activity;
    private AlertDialog dialog;

    public CarregandoDialog(Activity activity){
        this.activity = activity;
    }

    public void iniciarCarregandoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.TransparentAlertDialog);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_carregando, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void removerDialog(){
        dialog.dismiss();
    }

}

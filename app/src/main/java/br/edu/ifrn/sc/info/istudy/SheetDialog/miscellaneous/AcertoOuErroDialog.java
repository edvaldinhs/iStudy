package br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import br.edu.ifrn.sc.info.istudy.R;

public class AcertoOuErroDialog {

    private Activity activity;
    private AlertDialog dialog;
    private View view;

    public AcertoOuErroDialog(Activity activity, View view){
        this.activity = activity;
        this.view = view;
    }

    public void iniciarAcertoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.TransparentAlertDialog);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_acerto, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }
    public void iniciarErroDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.TransparentAlertDialog);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_erro, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        dialog.setOnDismissListener(listener);
    }

    public void removerDialog(){
        dialog.dismiss();
    }

}

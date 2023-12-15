package br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import br.edu.ifrn.sc.info.istudy.R;

public class AvisoDialog {

    private static final int DISPLAY_DURATION = 3000; // Duration to display the dialog in milliseconds

    private Activity activity;
    private PopupWindow popupWindow;
    private String texto;
    private TextView tvAviso;

    public AvisoDialog(Activity activity, String texto) {
        this.activity = activity;
        this.texto = texto;
    }

    public void iniciarAvisoDialog() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.dialog_aviso, null);

        tvAviso = popupView.findViewById(R.id.tvAviso);
        tvAviso.setText(texto);

        // Set up the PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.DialogAnimation_Top);

        // Show the popup at the top of the screen
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);

        // Schedule a task to remove the popup after a certain time
        popupView.postDelayed(new Runnable() {
            @Override
            public void run() {
                removerDialog();
            }
        }, DISPLAY_DURATION);
    }

    public void removerDialog() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}

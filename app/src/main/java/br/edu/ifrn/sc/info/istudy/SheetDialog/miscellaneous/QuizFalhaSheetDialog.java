package br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;

import br.edu.ifrn.sc.info.istudy.R;

public class QuizFalhaSheetDialog {

    private Activity activity;
    private Dialog dialog;
    private Button btnRefazerQuiz;
    private Button btnVoltarInicio;
    private TextView tvQAcertos;
    private TextView tvTempo;
    private TextView tvQErros;
    private int numAcertos;
    private int numErros;
    private String tempo;
    private NavController navController;
    private String email;
    private int conteudoId;
    private int dificuldadeId;

    public QuizFalhaSheetDialog(Activity activity, int numAcertos, int numErros, String tempo, NavController navController, int conteudoId, int dificuldadeId, String email){
        this.activity = activity;
        this.numAcertos = numAcertos;
        this.numErros = numErros;
        this.tempo = tempo;
        this.navController = navController;
        this.email = email;
        this.conteudoId = conteudoId;
        this.dificuldadeId = dificuldadeId;

    }

    public void iniciarDialog() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fim_atividade_falha);

        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        int desiredHeight = screenHeight - dpToPx(dialog.getContext(), 112);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, desiredHeight);
            window.setGravity(Gravity.BOTTOM);
            window.setDimAmount(0);
            window.setWindowAnimations(R.style.DialogAnimation);
        }

        dialog.setCancelable(false);
        dialog.show();

        tvQAcertos = dialog.findViewById(R.id.tvQAcertos);
        tvTempo = dialog.findViewById(R.id.tvTempo);
        tvQErros = dialog.findViewById(R.id.tvQErros);
        btnRefazerQuiz = dialog.findViewById(R.id.btnRefazerQuiz);

        tvQAcertos.setText(numAcertos+"");
        tvTempo.setText(tempo);
        tvQErros.setText(numErros+"");

        btnVoltarInicio = dialog.findViewById(R.id.btnVoltarInicio);

        btnVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerDialog();
            }
        });

        btnRefazerQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerDialog();
                iniciarQuiz();
            }
        });
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void removerDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void iniciarQuiz(){
        Bundle cartinha = new Bundle();

        cartinha.putInt("conteudoId", conteudoId);
        cartinha.putInt("dificuldadeId", dificuldadeId);
        cartinha.putString("email", email);

        if (navController != null) {
            navController.navigate(R.id.action_atividades_to_multiplaEscolha, cartinha);
            Log.d("ConteudoDescricaoFrag", navController.toString());
        } else {
            Log.e("ConteudoDescricaoFrag", "NavController is null");
        }
    }

}
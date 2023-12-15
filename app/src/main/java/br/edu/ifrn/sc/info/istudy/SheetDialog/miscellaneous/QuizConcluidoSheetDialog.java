package br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.SheetDialog.LoginCadastro.CadastroBottomSheetDialog;
import br.edu.ifrn.sc.info.istudy.SheetDialog.LoginCadastro.LoginBottomSheetDialog;

public class QuizConcluidoSheetDialog {

    private Activity activity;
    private Dialog dialog;
    private Button btnContinuar;
    private TextView tvPontuacao;
    private TextView tvQAcertos;
    private TextView tvTempo;
    private TextView tvQErros;
    private int pontuacao;
    private int numAcertos;
    private int numErros;
    private String tempo;

    public QuizConcluidoSheetDialog(Activity activity, int numAcertos, int numErros, int pontuacao, String tempo){
        this.activity = activity;
        this.numAcertos = numAcertos;
        this.numErros = numErros;
        this.tempo = tempo;
        this.pontuacao = pontuacao;
    }

    public void iniciarDialog() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fim_atividade_concluida);

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
        tvPontuacao = dialog.findViewById(R.id.tvPontuacao);

        tvPontuacao.setText("Você ganhou "+pontuacao+" pontos!\nE desbloqueou a próxima\natividade.");
        tvQAcertos.setText(numAcertos+"");
        tvTempo.setText(tempo);
        tvQErros.setText(numErros+"");

        btnContinuar = dialog.findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerDialog();
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

}
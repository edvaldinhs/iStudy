package br.edu.ifrn.sc.info.istudy.SheetDialog;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.SheetDialog.LoginCadastro.CadastroBottomSheetDialog;
import br.edu.ifrn.sc.info.istudy.SheetDialog.LoginCadastro.LoginBottomSheetDialog;

public class TelaInicialBottomSheetDialog extends BottomSheetDialogFragment {

    private Button btnLogin;
    private Button btnRegistrar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogAnimation);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_tela_inicial, container, false);

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setGravity(Gravity.BOTTOM);

        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarLoginDialog();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarCadastroDialog();
            }
        });
        return view;
    }

    private void mostrarLoginDialog(){
        LoginBottomSheetDialog bottomSheetDialog = new LoginBottomSheetDialog();
        bottomSheetDialog.show(getActivity().getSupportFragmentManager(), bottomSheetDialog.getTag());
    }

    private void mostrarCadastroDialog(){
        CadastroBottomSheetDialog bottomSheetDialog = new CadastroBottomSheetDialog();
        bottomSheetDialog.show(getActivity().getSupportFragmentManager(), bottomSheetDialog.getTag());
    }
}
package br.edu.ifrn.sc.info.istudy.SheetDialog.LoginCadastro;

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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import br.edu.ifrn.sc.info.istudy.R;

public class CadastroBottomSheetDialog extends BottomSheetDialogFragment {

    private Button btnCadastro;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogAnimation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cadastro, container, false);

        if (getActivity() != null) {
            View activityView = getActivity().findViewById(android.R.id.content);
            if (activityView != null) {
                ConstraintLayout background = activityView.findViewById(R.id.clTelaInicial);
                mudarBackgroundColor(background);
            }
        }

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setGravity(Gravity.BOTTOM);

        btnCadastro = view.findViewById(R.id.btnCadastrar);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), TelaPrincipal.class);
//                startActivity(intent);
//                getActivity().finish();
            }
        });

        return view;
    }
    public void mudarBackgroundColor(View view){

        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(
                view,
                "backgroundColor",
                new ArgbEvaluator(),
                getResources().getColor(R.color.white), // Start color
                getResources().getColor(R.color.aristofany_white)    // End color
        );

        // Set the duration of the animation (in milliseconds)
        colorAnimator.setDuration(500); // 3000 milliseconds = 3 seconds

        // Start the animation
        colorAnimator.start();
    }
}
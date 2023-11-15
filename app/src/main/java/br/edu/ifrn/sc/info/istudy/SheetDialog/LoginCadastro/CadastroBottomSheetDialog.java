package br.edu.ifrn.sc.info.istudy.SheetDialog.LoginCadastro;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.gerenciadorDeArquivo.SecureStorageHelper;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.telas.TelaInicial;
import br.edu.ifrn.sc.info.istudy.telas.TelaPrincipal;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroBottomSheetDialog extends BottomSheetDialogFragment {

    private Button btnCadastro;
    private TextView etNome;
    private TextView etEmail;
    private TextView etSenha;

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

        etNome = view.findViewById(R.id.etNome);
        etEmail = view.findViewById(R.id.etEmailAddress);
        etSenha = view.findViewById(R.id.etPassword);

        btnCadastro = view.findViewById(R.id.btnCadastrar);



        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNome.getText() == null || etNome.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Insira seu Nome antes", Toast.LENGTH_LONG).show();
                }else{
                    if(etEmail.getText() == null || etEmail.getText().toString().isEmpty()){
                        Toast.makeText(getActivity(), "Insira seu Email antes", Toast.LENGTH_LONG).show();
                    }else{
                        if(etSenha.getText() == null || etSenha.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(), "Insira sua Senha antes", Toast.LENGTH_LONG).show();
                        }else{
                            verificarEmail(etNome.getText().toString(), etEmail.getText().toString(), etSenha.getText().toString());
                        }
                    }
                }

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
    public void retornarBackgroundColor(View view){

        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(
                view,
                "backgroundColor",
                new ArgbEvaluator(),
                getResources().getColor(R.color.aristofany_white),
                getResources().getColor(R.color.white)
        );

        // Set the duration of the animation (in milliseconds)
        colorAnimator.setDuration(500); // 3000 milliseconds = 3 seconds

        // Start the animation
        colorAnimator.start();
    }

    private void cadastrar(String nome, String email, String senha){
        Estudante estudante = new Estudante();
        estudante.setNome(nome);
        estudante.setEmail(email);
        estudante.setSenha(senha);

        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call< Boolean > metodoInserir = estudanteWS.cadastrarEstudante(nome, email, senha);

        metodoInserir.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("Cadastro", response.body()+"");
                if(response.body() != null && response.body()){
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("senha", senha);

                    try {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("email", email);
                        jsonObject.put("senha", senha);

                        String jsonString = jsonObject.toString();
                        Log.d("tste", jsonString);

                        SecureStorageHelper.saveData(getActivity(), "usuario.json", jsonString);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intent = new Intent(getActivity(), TelaPrincipal.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    Log.d("Cadastro", "Erro na inserção do usuário.");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }
    private void verificarEmail(String nome, String email, String senha){
        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<List<Estudante>> metodoListar = estudanteWS.listarTodos();

        metodoListar.enqueue(new Callback<List<Estudante>>() {
            @Override
            public void onResponse(Call<List<Estudante>> call, Response<List<Estudante>> response) {
                boolean emailIgual = false;
                for (Estudante estudante : response.body()){
                    if(estudante.getEmail().equals(email)){
                        emailIgual = true;
                    }
                }
                if(emailIgual){
                    Toast.makeText(getActivity(), "Email Já foi utilizado por outro usuário", Toast.LENGTH_LONG).show();
                }else{
                    cadastrar(nome, email, senha);
                }
            }

            @Override
            public void onFailure(Call<List<Estudante>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        if (getActivity() != null) {
            View activityView = getActivity().findViewById(android.R.id.content);
            if (activityView != null) {
                ConstraintLayout background = activityView.findViewById(R.id.clTelaInicial);
                retornarBackgroundColor(background);
            }
        }
    }
}
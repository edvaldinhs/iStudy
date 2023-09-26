package br.edu.ifrn.sc.info.istudy.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaInicial extends AppCompatActivity {

    private Button botaoCliqueAqui;
    private EditText etTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        botaoCliqueAqui = findViewById(R.id.botaoConfirmar);
        etTelefone = findViewById(R.id.etTelefone);


        botaoCliqueAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buscarDisciplinas();

                //  Intent intent = new Intent(TelaInicial.this, TelaDisciplinas.class);
              //  startActivity(intent);

            }
        });

    }

    public void buscarDisciplinas() {

        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<List<Disciplina>> metodoListar = disciplinaWS.listarTodas();

        metodoListar.enqueue(new Callback<List<Disciplina>>() {
            @Override
            public void onResponse(Call<List<Disciplina>> call, Response<List<Disciplina>> resposta) {
                List<Disciplina> disciplinas = resposta.body();
                etTelefone.setText(disciplinas.get(0).getNome());
            }

            @Override
            public void onFailure(Call<List<Disciplina>> call, Throwable t) {

                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
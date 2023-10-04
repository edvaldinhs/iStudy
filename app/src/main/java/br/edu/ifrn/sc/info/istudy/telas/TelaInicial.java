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

    private EditText etViewBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        botaoCliqueAqui = findViewById(R.id.botaoConfirmar);
        etTelefone = findViewById(R.id.etTelefone);
        etViewBuscar = findViewById(R.id.viewBuscar);
        Disciplina disciplina = new Disciplina(3, "Redação");

        botaoCliqueAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarDisciplinasPeloID(1);

            }
        });

    }

    public void inserirDisciplina(Disciplina disciplina){
        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<Boolean> metodoInserir = disciplinaWS.inserir(disciplina);

        metodoInserir.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Disciplina Inserida", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void removerDisciplina(int id){
        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<Boolean> metodoRemover = disciplinaWS.remover(id);

        metodoRemover.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Disciplina Removida", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
//    public void buscarDisciplinas() {
//
//        RetrofitConfig config = new RetrofitConfig();
//        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
//        Call<List<Disciplina>> metodoListar = disciplinaWS.listarTodas();
//
//        metodoListar.enqueue(new Callback<List<Disciplina>>() {
//            @Override
//            public void onResponse(Call<List<Disciplina>> call, Response<List<Disciplina>> resposta) {
//                List<Disciplina> disciplinas = resposta.body();
//
//                try {
//                    int id = Integer.parseInt(etViewBuscar.getText().toString());
//                    if(id > disciplinas.size() || id < 0){
//                        Toast.makeText(TelaInicial.this, "Id inexistente", Toast.LENGTH_LONG).show();
//                    }else{
//                        etTelefone.setText(disciplinas.get(id-1).getNome());
//                    }
//                }catch(Exception e){
//                    Toast.makeText(TelaInicial.this, "Digite um número", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Disciplina>> call, Throwable t) {
//
//                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
    public void buscarDisciplinasPeloID(int id) {

        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<Disciplina> metodoBuscar = disciplinaWS.buscar(id);

        metodoBuscar.enqueue(new Callback<Disciplina>() {
            @Override
            public void onResponse(Call<Disciplina> call, Response<Disciplina> response) {
                Disciplina disciplina = response.body();
                etTelefone.setText(disciplina.getNome());
            }

            @Override
            public void onFailure(Call<Disciplina> call, Throwable t) {

            }
        });
    }
}
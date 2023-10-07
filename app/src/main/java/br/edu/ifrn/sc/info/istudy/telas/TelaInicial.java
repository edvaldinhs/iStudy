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
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.dominio.Titulo;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import br.edu.ifrn.sc.info.istudy.ws.TituloWS;
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
        Estudante estudante = new Estudante("rebequinha@gmail.com", 100, "Eduarda", "321", "", new Titulo(2, "Intermediário"));


        botaoCliqueAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Disciplina disciplina = new Disciplina();
//                int id = Integer.parseInt(etViewBuscar.getText().toString());
//                String email = etTelefone.getText().toString();
//                disciplina.setId(id);
//                disciplina.setNome(nome);
//                atualizarDisciplina(disciplina);

//                inserirEstudante(estudante);

//                buscarEstudante(email);

                Intent intent = new Intent(TelaInicial.this, TelaPrincipal.class);
                startActivity(intent);
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
    public void atualizarDisciplina(Disciplina disciplina) {

        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<Boolean> metodoAtualizar = disciplinaWS.atualizar(disciplina);

        metodoAtualizar.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Disciplina Atualizada", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void inserirEstudante(Estudante estudante){
        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<Boolean> metodoInserir = estudanteWS.inserir(estudante);

        metodoInserir.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Estudante Inserido", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void removerEstudante(String email){

        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<Boolean> metodoRemover = estudanteWS.remover(email);

        metodoRemover.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Estudante Removido", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void buscarEstudante(String email) {

        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<Estudante> metodoBuscar = estudanteWS.buscar(email);

        metodoBuscar.enqueue(new Callback<Estudante>() {
            @Override
            public void onResponse(Call<Estudante> call, Response<Estudante> response) {
                Estudante estudante = response.body();
                etTelefone.setText(estudante.getNome());
            }

            @Override
            public void onFailure(Call<Estudante> call, Throwable t) {

            }
        });
    }
    public void atualizarEstudante(Estudante estudante) {

        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call<Boolean> metodoAtualizar = estudanteWS.atualizar(estudante);

        metodoAtualizar.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Estudante Atualizado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inserirConteudo(Conteudo conteudo){
        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Boolean> metodoInserir = conteudoWS.inserir(conteudo);

        metodoInserir.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Conteudo Inserido", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void removerConteudo(int id){

        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Boolean> metodoRemover = conteudoWS.remover(id);

        metodoRemover.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Conteudo Removido", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void buscarConteudo(int id) {

        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Conteudo> metodoBuscar = conteudoWS.buscar(id);

        metodoBuscar.enqueue(new Callback<Conteudo>() {
            @Override
            public void onResponse(Call<Conteudo> call, Response<Conteudo> response) {
                Conteudo conteudo = response.body();
                etTelefone.setText(conteudo.getNome());
            }

            @Override
            public void onFailure(Call<Conteudo> call, Throwable t) {

            }
        });
    }
    public void atualizarConteudo(Conteudo conteudo) {

        RetrofitConfig config = new RetrofitConfig();
        ConteudoWS conteudoWS = config.getConteudoWS();
        Call<Boolean> metodoAtualizar = conteudoWS.atualizar(conteudo);

        metodoAtualizar.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Conteudo Atualizado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inserirTitulo(Titulo titulo){
        RetrofitConfig config = new RetrofitConfig();
        TituloWS tituloWS = config.getTituloWS();
        Call<Boolean> metodoInserir = tituloWS.inserir(titulo);

        metodoInserir.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Titulo Inserido", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void removerTitulo(int id){
        RetrofitConfig config = new RetrofitConfig();
        TituloWS tituloWS = config.getTituloWS();
        Call<Boolean> metodoRemover = tituloWS.remover(id);

        metodoRemover.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Titulo Removido", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void buscarTitulo(int id) {

        RetrofitConfig config = new RetrofitConfig();
        TituloWS tituloWS = config.getTituloWS();
        Call<Titulo> metodoBuscar = tituloWS.buscar(id);

        metodoBuscar.enqueue(new Callback<Titulo>() {
            @Override
            public void onResponse(Call<Titulo> call, Response<Titulo> response) {
                Titulo titulo = response.body();
                etTelefone.setText(titulo.getDescricao());
            }

            @Override
            public void onFailure(Call<Titulo> call, Throwable t) {

            }
        });
    }
    public void atualizarTitulo(Titulo titulo) {

        RetrofitConfig config = new RetrofitConfig();
        TituloWS tituloWS = config.getTituloWS();
        Call<Boolean> metodoAtualizar = tituloWS.atualizar(titulo);

        metodoAtualizar.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(TelaInicial.this, "Titulo Atualizado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(TelaInicial.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
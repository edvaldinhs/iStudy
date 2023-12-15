package br.edu.ifrn.sc.info.istudy.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.edu.ifrn.sc.info.istudy.R;

public class SegundaTela extends AppCompatActivity {

    Button botaoVolteAqui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_tela);

        botaoVolteAqui = findViewById(R.id.botaoVoltar);
        botaoVolteAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SegundaTela.this, TelaInicial.class);
                startActivity(intent);
            }
        });
    }
}
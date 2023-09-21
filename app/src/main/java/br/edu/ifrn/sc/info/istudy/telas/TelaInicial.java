package br.edu.ifrn.sc.info.istudy.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.edu.ifrn.sc.info.istudy.R;

public class TelaInicial extends AppCompatActivity {

    Button botaoCliqueAqui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        botaoCliqueAqui = findViewById(R.id.botaoConfirmar);
        botaoCliqueAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicial.this, TelaDisciplinas.class);
                startActivity(intent);

            }
        });

    }
}
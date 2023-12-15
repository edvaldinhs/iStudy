package br.edu.ifrn.sc.info.istudy.telas.fragment.Quiz;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Cronometro {
    private boolean taContando = false;
    private int segundos = 0;
    private String tempo;
    private Handler handler = new Handler();

    public void iniciarTimer(View view) {
        taContando = true;
        handler.postDelayed(runnable, 1000);
    }

    public void pararTimer(View view) {
        taContando = false;
    }
    
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (taContando) {
                segundos++;
                int minutes = (segundos % 3600) / 60;
                int secs = segundos % 60;

                tempo = String.format("%02d:%02d", minutes, secs);

                handler.postDelayed(this, 1000);
            }
        }
    };
    public int getSegundos(){
        return segundos;
    }

    public String getTempo(){
        return tempo;
    }
}

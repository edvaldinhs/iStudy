package br.edu.ifrn.sc.info.istudy.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import br.edu.ifrn.sc.info.istudy.R;

public class TelaDisciplinas extends AppCompatActivity {


    TextView iStudyLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_disciplinas);

        iStudyLogo = findViewById(R.id.iStudyLogo);

        String i = getColoredSpanned("i", "#7EBA2F");
        String study = getColoredSpanned("Study","#A547F9");

        iStudyLogo.setText(Html.fromHtml(i+study));
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}
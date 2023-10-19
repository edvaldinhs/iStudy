package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Atividade;

public class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Atividade atividade;
    private ConstraintLayout clCardQuiz;
    private TextView tvNomeQuiz;

    public QuizHolder(@NonNull View itemView) {
        super(itemView);
        clCardQuiz = itemView.findViewById(R.id.card_quiz);
        tvNomeQuiz = itemView.findViewById(R.id.tvNomeQuiz);
        clCardQuiz.setOnClickListener(this);
    }

    public void bind(Atividade atividade) {
        this.atividade = atividade;

        mudarDeCor();

        if(!atividade.getNome().isEmpty()){
            tvNomeQuiz.setText(atividade.getNome());
        }
    }

    private void mudarDeCor(){

        if(atividade.getConteudo().getDisciplina().getId() == 1){
            clCardQuiz.setBackgroundResource(R.drawable.istudy_aprenda_port);

        }else if (atividade.getConteudo().getDisciplina().getId() == 2){
            clCardQuiz.setBackgroundResource(R.drawable.istudy_aprenda_mat);
        }else {
            clCardQuiz.setBackgroundResource(R.drawable.istudy_aprenda_port);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.card_quiz) {
            try {

            } catch (NullPointerException nullPointerException) {

            }
        }
    }
}
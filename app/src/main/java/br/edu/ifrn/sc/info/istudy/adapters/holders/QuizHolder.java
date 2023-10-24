package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConteudoClickListener;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnQuizClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;

public class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Conteudo quiz;
    private ConstraintLayout clCardQuiz;
    private TextView tvNomeQuiz;

    ImageView icone;

    private OnQuizClickListener onQuizClickListener;

    public QuizHolder(@NonNull View itemView, OnQuizClickListener listener) {
        super(itemView);
        clCardQuiz = itemView.findViewById(R.id.card_quiz);
        tvNomeQuiz = itemView.findViewById(R.id.tvNomeQuiz);
        icone = itemView.findViewById(R.id.ivIcone);
        onQuizClickListener = listener;
        clCardQuiz.setOnClickListener(this);
    }

    public void bind(Conteudo quiz) {
        this.quiz = quiz;

        mudarDeCor();

        if(!quiz.getNome().isEmpty()){
            tvNomeQuiz.setText(quiz.getNome());
        }
    }

    private void mudarDeCor(){
        if(quiz.getDisciplina().getId() == 1 && quiz.getBloqueado() == false){
            clCardQuiz.setBackgroundResource(R.drawable.card_quiz_conteudo_port);
        }else if (quiz.getDisciplina().getId() == 2 && quiz.getBloqueado() == false){
            clCardQuiz.setBackgroundResource(R.drawable.card_quiz_conteudo_mat);
            mudarIcone();
        }else {
            clCardQuiz.setBackgroundResource(R.drawable.card_quiz_conteudo_bloqueado);
            mudarIcone();
        }
    }

    //Método só para a print, se eu esquecer de tirar isso, removam pls.
    private void mudarIcone(){
        if(quiz.getId() == 7){
            icone.setBackgroundResource(R.drawable.ic_cube);
        }else if (quiz.getId() == 8){
            icone.setBackgroundResource(R.drawable.ic_calculator);
        }else if (quiz.getId() == 9) {
            icone.setBackgroundResource(R.drawable.ic_percent);
        }else {
            icone.setBackgroundResource(R.drawable.ic_desktop);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.card_quiz) {
            try {
                onQuizClickListener.onQuizClick(quiz.getId());
            } catch (NullPointerException nullPointerException) {

            }
        }
    }
}

package br.edu.ifrn.sc.info.istudy.adapters.holders;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnQuizzesByDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;

public class QuizzesByDisciplinaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Cria disciplina, pois será necessário receber os dados
    private Disciplina disciplina;

    //Cria "cl" e "tv" para modificar dados no card
    private ConstraintLayout clCardQuizByDisciplina;
    private TextView tvNomeDisciplina;

    OnQuizzesByDisciplinaClickListener onQuizzesByDisciplinaClick;

    NavController navController;

    public QuizzesByDisciplinaHolder(@NonNull View itemView, OnQuizzesByDisciplinaClickListener listener, NavController navController) {
        super(itemView);

        //Diz quem é quem, e de onde veio cada um
        clCardQuizByDisciplina = itemView.findViewById(R.id.card_quiz_by_disciplina);
        tvNomeDisciplina = itemView.findViewById(R.id.titulo_disciplina);

        onQuizzesByDisciplinaClick = listener;
        this.navController = navController;
        clCardQuizByDisciplina.setOnClickListener(this);
    }

    //Método pra modificar os dados no card
    public void bind(Disciplina disciplina) {
        this.disciplina = disciplina;

        mudarDeCor();

        //Se a disciplina tiver um nome, o insere no textView
        if(!disciplina.getNome().isEmpty()){
            tvNomeDisciplina.setText(disciplina.getNome());
        }
    }

    //Método para mudar a cor do card
    private void mudarDeCor(){
        //sugestao[talvez mudar pra switch case, em vez de if else]

        //Utiliza o id para diferenciar cada matéria e muda o background
        if(disciplina.getId() == 1){
            clCardQuizByDisciplina.setBackgroundResource(R.drawable.card_quiz_lingua_portuguesa);
        }else if (disciplina.getId() == 2){
            clCardQuizByDisciplina.setBackgroundResource(R.drawable.card_quiz_matematica);
        }else {
            clCardQuizByDisciplina.setBackgroundResource(R.drawable.card_quiz_lingua_portuguesa);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.card_quiz_by_disciplina) {
            try {
                onQuizzesByDisciplinaClick.onQuizzesByDisciplinaClick(disciplina.getId());
            } catch (NullPointerException nullPointerException) {

            }
        }
    }
}

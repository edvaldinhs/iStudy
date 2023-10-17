package br.edu.ifrn.sc.info.istudy.adapters.holders;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;

public class DisciplinasHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Cria disciplina, pois será necessário receber os dados
    private Disciplina disciplina;

    //Cria "cl" e "tv" para modificar dados no card
    private ConstraintLayout clCardDisciplina;
    private TextView tvNomeDisciplina;

    private OnDisciplinaClickListener disciplinaClickListener;

    private NavController navController;

    public DisciplinasHolder(@NonNull View itemView, OnDisciplinaClickListener disciplinaClickListener, NavController navController) {
        super(itemView);

        //Diz quem é quem, e de onde veio cada um
        clCardDisciplina = itemView.findViewById(R.id.card_disciplina);
        tvNomeDisciplina = itemView.findViewById(R.id.tvNomeDisciplina);

        this.disciplinaClickListener = disciplinaClickListener;
        this.navController = navController;
        clCardDisciplina.setOnClickListener(this);


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
            clCardDisciplina.setBackgroundResource(R.drawable.istudy_aprenda_port);
        }else if (disciplina.getId() == 2){
            clCardDisciplina.setBackgroundResource(R.drawable.istudy_aprenda_mat);
        }else {
            clCardDisciplina.setBackgroundResource(R.drawable.istudy_aprenda_port);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.card_disciplina) {
            try {
                disciplinaClickListener.onDisciplinaClick(disciplina.getId());
            } catch (NullPointerException nullPointerException) {

            }
        }
//        switch (viewId) {
//            case R.id.card_disciplina:
//                try {
//                    disciplinaClickListener.onDisciplinaClick(disciplina.getId());
//                }catch (NullPointerException nullPointerException){
//
//                }
//                break;
//            default:
//                break;
//        }
    }
}

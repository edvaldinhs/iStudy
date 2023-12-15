package br.edu.ifrn.sc.info.istudy.adapters.holders;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private Context mContext;


    //Cria "cl" e "tv" para modificar dados no card
    private ConstraintLayout clCardDisciplina;
    private TextView tvNomeDisciplina;

    private OnDisciplinaClickListener disciplinaClickListener;

    private NavController navController;

    public DisciplinasHolder(@NonNull final View itemView,Context context, OnDisciplinaClickListener disciplinaClickListener, NavController navController) {
        super(itemView);

        //Diz quem é quem, e de onde veio cada um
        clCardDisciplina = itemView.findViewById(R.id.card_disciplina);
        tvNomeDisciplina = itemView.findViewById(R.id.tvNomeDisciplina);

        mContext = context;
        this.disciplinaClickListener = disciplinaClickListener;
        this.navController = navController;
        clCardDisciplina.setOnClickListener(this);

        itemView.setClickable(true);
        itemView.setFocusable(true);
        itemView.setFocusableInTouchMode(true);

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
    public void adicionarMargin(Boolean esqOuDir){
        if(esqOuDir){
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = dpToPx(80);
            itemView.setLayoutParams(layoutParams);
        } else{
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            layoutParams.rightMargin = dpToPx(80);
            itemView.setLayoutParams(layoutParams);
        }

    }

    private int dpToPx(int dp) {
        float density = itemView.getContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
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
    }

}

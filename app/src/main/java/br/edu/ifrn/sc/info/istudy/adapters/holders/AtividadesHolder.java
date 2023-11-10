package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnAtividadeClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Atividade;

public class AtividadesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Atividade atividade;
    private ConstraintLayout clCardAtividade;
    private OnAtividadeClickListener onAtividadeClickListener;

    public AtividadesHolder(@NonNull View itemView, OnAtividadeClickListener onAtividadeClickListener) {
        super(itemView);
    }

    public void bind(Atividade atividade) {
        this.atividade = atividade;
        mudarDeCor();
    }

    private void mudarDeCor(){
        Log.d("mudarDeCor", "Bloqueado: " + atividade.getConteudo().getBloqueado());

        if(atividade.getConteudo().getId() == 1 && atividade.getConteudo().getBloqueado() == false){
            clCardAtividade.setBackgroundResource(R.drawable.card_dificuldade_port);
        }else if (atividade.getConteudo().getId() == 2 && atividade.getConteudo().getBloqueado() == false){
            clCardAtividade.setBackgroundResource(R.drawable.card_dificuldade_mat);
        }else {
            clCardAtividade.setBackgroundResource(R.drawable.card_dificuldade_bloqueado);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.card_nivel) {
            try {
                Log.d("onClick", "Bloqueado: " + atividade.getConteudo().getBloqueado());
                if (atividade.getConteudo().getBloqueado() == false) {
                    onAtividadeClickListener.onAtividadeClick(atividade.getId());
                }

            } catch (NullPointerException nullPointerException) {

            }
        }
    }

}

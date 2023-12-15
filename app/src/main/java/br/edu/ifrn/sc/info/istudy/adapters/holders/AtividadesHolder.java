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

    public AtividadesHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(Atividade atividade) {
        this.atividade = atividade;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.firstAtividade) {
            try {

            } catch (NullPointerException nullPointerException) {

            }
        }
    }

}

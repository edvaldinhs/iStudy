package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnIconeClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Icone;

public class IconeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Icone icone;
    private ConstraintLayout clCardIcone;
    private OnIconeClickListener onIconeClickListener;

    public IconeHolder(@NonNull View itemView, OnIconeClickListener onIconeClickListener) {
        super(itemView);
        clCardIcone = itemView.findViewById(R.id.card_icone);
        this.onIconeClickListener = onIconeClickListener;
        clCardIcone.setOnClickListener(this);
    }

    public void bind(Icone icone) {
        this.icone = icone;
        mudarDeIcone();
    }

    private void mudarDeIcone(){
        clCardIcone.setBackground(icone.getIcone());
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.card_nivel) {
            try {


            } catch (NullPointerException nullPointerException) {

            }
        }
    }

}

package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;

public class ConquistasHolder extends RecyclerView.ViewHolder {
    private Conquista conquista;
    private ConstraintLayout clCardConquista;
    ImageView icone;

    public ConquistasHolder(@NonNull View itemView) {
        super(itemView);
        clCardConquista = itemView.findViewById(R.id.card_conquista);
        icone = itemView.findViewById(R.id.ivIcone);
    }

    public void bind(Conquista conquista) {
        this.conquista = conquista;

        mudarDeCor();
    }

    private void mudarDeCor(){
       
    }

    //Método só para a print, se eu esquecer de tirar isso, removam pls.
    private void mudarIcone(){
        if(conquista.getId() == 7){
            icone.setBackgroundResource(R.drawable.ic_cube);
        }else if (conquista.getId() == 8){
            icone.setBackgroundResource(R.drawable.ic_calculator);
        }else if (conquista.getId() == 9) {
            icone.setBackgroundResource(R.drawable.ic_percent);
        }else {
            icone.setBackgroundResource(R.drawable.ic_desktop);
        }
    }
}

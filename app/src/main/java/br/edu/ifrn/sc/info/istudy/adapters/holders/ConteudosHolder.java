package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;

public class ConteudosHolder extends RecyclerView.ViewHolder {

    private Conteudo conteudo;

    private ConstraintLayout clCardConteudo;
    private TextView tvNomeConteudo;

    public ConteudosHolder(@NonNull View itemView) {
        super(itemView);

        clCardConteudo = itemView.findViewById(R.id.card_conteudo);
        tvNomeConteudo = itemView.findViewById(R.id.tvNomeConteudo);
    }

    public void bind(Conteudo conteudo) {
        this.conteudo = conteudo;

        mudarDeCor();

        if(!conteudo.getNome().isEmpty()){
            tvNomeConteudo.setText(conteudo.getNome());
        }
    }

    private void mudarDeCor(){

        if(conteudo.getId() == 1){
            clCardConteudo.setBackgroundResource(R.drawable.card_conteudo_port);
        }else if (conteudo.getId() == 2){
            clCardConteudo.setBackgroundResource(R.drawable.card_conteudo_mat);
        }else {
            clCardConteudo.setBackgroundResource(R.drawable.card_conteudo_port);
        }
    }

}

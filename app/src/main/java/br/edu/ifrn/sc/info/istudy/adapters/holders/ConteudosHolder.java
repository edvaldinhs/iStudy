package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConteudoClickListener;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnQuizzesByDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConteudosHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Conteudo conteudo;

    private ConstraintLayout clCardConteudo;
    private TextView tvNomeConteudo;

    private OnConteudoClickListener conteudoClickListener;

    private String emailUsuario;

    public ConteudosHolder(@NonNull View itemView, OnConteudoClickListener listener, String emailUsuario) {
        super(itemView);
        clCardConteudo = itemView.findViewById(R.id.card_conteudo);
        tvNomeConteudo = itemView.findViewById(R.id.tvNomeConteudo);
        clCardConteudo.setOnClickListener(this);
        this.emailUsuario = emailUsuario;

        conteudoClickListener = listener;
    }

    public void bind(Conteudo conteudo) {
        this.conteudo = conteudo;


        mudarDeCor();

        if(!conteudo.getNome().isEmpty()){
            tvNomeConteudo.setText(conteudo.getNome());
        }
    }

    private void mudarDeCor(){
        Log.d("mudarDeCor", "Bloqueado: " + conteudo.getBloqueado());

        if(conteudo.getDisciplina().getId() == 1 && conteudo.getBloqueado() == false){
            clCardConteudo.setBackgroundResource(R.drawable.card_conteudo_port);
        }else if (conteudo.getDisciplina().getId() == 2 && conteudo.getBloqueado() == false){
            clCardConteudo.setBackgroundResource(R.drawable.card_conteudo_mat);
        }else {
            clCardConteudo.setBackgroundResource(R.drawable.card_conteudo_bloqueado);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.card_conteudo) {
            try {
                Log.d("onClick", "Bloqueado: " + conteudo.getBloqueado());
                if (conteudo.getBloqueado() == false) {
                    conteudoClickListener.onConteudoClick(conteudo.getId());
                }

            } catch (NullPointerException nullPointerException) {

            }
        }
    }
}

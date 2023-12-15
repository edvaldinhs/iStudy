package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.dominio.Icone;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingHolder extends RecyclerView.ViewHolder {
    private Estudante estudante;
    private List<Icone> icones;
    private TextView tvNomeAluno;
    private TextView tvPontuacao;
    private ImageView foto;

    public RankingHolder(@NonNull View itemView, List<Icone> icones) {
        super(itemView);
        this.icones = icones;
        foto = itemView.findViewById(R.id.ivFotoAlunoCard);
        tvNomeAluno = itemView.findViewById(R.id.tvNomeAluno);
        tvPontuacao = itemView.findViewById(R.id.tvPontos);
    }

    public void bind(Estudante estudante) {
        this.estudante = estudante;
        preencherDados();
    }

    private void preencherDados(){
        try {
            tvNomeAluno.setText(estudante.getNome());
            tvPontuacao.setText(estudante.getPontuacao()+"");

            String estudanteFoto = estudante.getFoto();
            for (Icone icone : icones){
                if (estudanteFoto != null && estudanteFoto.equals(icone.getId())){
                    foto.setImageDrawable(icone.getIcone());
                }
            }
        }catch(NullPointerException nullPointerException){
            Log.e("RankingHolder", nullPointerException.getMessage());
        }
    }
}

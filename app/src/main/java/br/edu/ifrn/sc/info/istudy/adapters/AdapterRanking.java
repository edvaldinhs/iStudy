package br.edu.ifrn.sc.info.istudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.RankingHolder;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.dominio.Icone;

public class AdapterRanking extends RecyclerView.Adapter<RankingHolder> {

    private Context mContext;

    List<Estudante> estudantes;
    private List<Icone> icones;

    public AdapterRanking(Context context, List<Estudante> estudantes, List<Icone> icones){
        mContext = context;
        this.estudantes = estudantes;
        this.icones = icones;
    }

    public RankingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_estudante, parent, false);
        RankingHolder holder = new RankingHolder(view, icones);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankingHolder holder, int position) {
        Estudante tempEstudante = estudantes.get(position);
        holder.bind(tempEstudante);
    }

    public void clearData() {
        estudantes.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return estudantes.size();
    }

}

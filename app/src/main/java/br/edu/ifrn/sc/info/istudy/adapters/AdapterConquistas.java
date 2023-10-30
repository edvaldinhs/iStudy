package br.edu.ifrn.sc.info.istudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.ConquistasHolder;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;

public class AdapterConquistas extends RecyclerView.Adapter<ConquistasHolder> {

    private Context mContext;

    List<Conquista> conquistas;

    public AdapterConquistas(Context context, List<Conquista> conquistas){
        mContext = context;
        this.conquistas = conquistas;
    }

    public ConquistasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_conquista, parent, false);
        ConquistasHolder holder = new ConquistasHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConquistasHolder holder, int position) {
        Conquista tempConquista = conquistas.get(position);
        holder.bind(tempConquista);
    }

    public void clearData() {
        conquistas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return conquistas.size();
    }

}

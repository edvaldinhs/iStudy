package br.edu.ifrn.sc.info.istudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.ConteudosHolder;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;

public class AdapterConteudos extends RecyclerView.Adapter<ConteudosHolder> {

    private Context mContext;

    List<Conteudo> conteudos;

    public AdapterConteudos(Context context, List<Conteudo> conteudos){
        mContext = context;
        this.conteudos = conteudos;
    }

    @NonNull
    @Override
    public ConteudosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_disciplina, parent, false);
        return new ConteudosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConteudosHolder holder, int position) {
        Conteudo tempConteudo = conteudos.get(position);
        holder.bind(tempConteudo);
    }

    @Override
    public int getItemCount() {
        return conteudos.size();
    }

}

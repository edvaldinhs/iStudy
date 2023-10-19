package br.edu.ifrn.sc.info.istudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.ConteudosHolder;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;

public class AdapterConteudos extends RecyclerView.Adapter<ConteudosHolder> {

    private Context mContext;

    List<Conteudo> conteudos;

    NavController navController;

    public AdapterConteudos(Context context, List<Conteudo> conteudos, NavController navController){
        mContext = context;
        this.conteudos = conteudos;
        this.navController = navController;
    }

    public ConteudosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_conteudo, parent, false);
        ConteudosHolder holder = new ConteudosHolder(view);
        view.setOnClickListener(holder);
        return holder;
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

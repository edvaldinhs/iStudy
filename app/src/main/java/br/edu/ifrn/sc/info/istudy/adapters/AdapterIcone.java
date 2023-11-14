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
import br.edu.ifrn.sc.info.istudy.adapters.holders.IconeHolder;
import br.edu.ifrn.sc.info.istudy.adapters.holders.IconeHolder;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnIconeClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Icone;

public class AdapterIcone extends RecyclerView.Adapter<IconeHolder> {

    //contexto pois será necessário num fragment
    private Context mContext;

    //ArrayList para guardar as icones
    List<Icone> icones;

    private int posicaoEsquerda = -1;
    private int posicaoDireita = -1;

    private OnIconeClickListener iconeClickListener;

    //Construtor
    public AdapterIcone(Context context, List<Icone> icones, OnIconeClickListener listener){
        mContext = context;
        this.icones = icones;
        iconeClickListener = listener;
    }

    public IconeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_icone, parent, false);
        IconeHolder holder = new IconeHolder(view, iconeClickListener);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IconeHolder holder, int position) {
        Icone tempIcone = icones.get(position);
        holder.bind(tempIcone, position);

//        if(position == 0){
//            holder.adicionarMargin(true);
//        } else if (position == getItemCount() - 1) {
//            holder.adicionarMargin(false);
//        }
    }

    @Override
    public int getItemCount() {
        //método pra saber quantos itens existem
        return icones.size();
    }

}

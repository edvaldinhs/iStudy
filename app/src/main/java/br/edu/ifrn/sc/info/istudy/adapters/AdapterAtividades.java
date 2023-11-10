package br.edu.ifrn.sc.info.istudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.AtividadesHolder;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnAtividadeClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Atividade;

public class AdapterAtividades extends RecyclerView.Adapter<AtividadesHolder>{

    private Context mContext;

    List<Atividade> niveis;

    OnAtividadeClickListener onAtividadeClickListener;

    public AdapterAtividades(Context context, List<Atividade> niveis, OnAtividadeClickListener listener){
        mContext = context;
        this.niveis = niveis;
        onAtividadeClickListener = listener;
    }

    public AtividadesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_nivel, parent, false);
        AtividadesHolder holder = new AtividadesHolder(view, onAtividadeClickListener);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AtividadesHolder holder, int position) {
        Atividade tempAtividade = niveis.get(position);
        holder.bind(tempAtividade);
    }

    @Override
    public int getItemCount() {
        return niveis.size();
    }

}

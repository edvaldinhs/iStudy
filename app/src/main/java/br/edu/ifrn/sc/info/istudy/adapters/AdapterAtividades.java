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
import br.edu.ifrn.sc.info.istudy.dominio.Atividade;

public class AdapterAtividades extends RecyclerView.Adapter<AtividadesHolder>{
    private Context mContext;
    private List<Atividade> niveis;

    public AdapterAtividades(Context context, List<Atividade> niveis){
        mContext = context;
        this.niveis = niveis;
    }

    public AtividadesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_atividade_port, parent, false);
        AtividadesHolder holder = new AtividadesHolder(view);
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

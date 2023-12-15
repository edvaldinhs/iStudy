package br.edu.ifrn.sc.info.istudy.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.ConquistasHolder;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConquistaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;

public class AdapterConquistas extends RecyclerView.Adapter<ConquistasHolder> {

    private Context mContext;
    private Activity activity;

    private List<Conquista> conquistas;
    private OnConquistaClickListener conquistaClickListener;
    private String email;

    public AdapterConquistas(Context context, Activity activity, List<Conquista> conquistas, OnConquistaClickListener listener, String email){
        mContext = context;
        this.conquistas = conquistas;
        conquistaClickListener = listener;
        this.email = email;
        this.activity = activity;
    }

    public ConquistasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_conquista, parent, false);
        ConquistasHolder holder = new ConquistasHolder(view, activity, conquistaClickListener, email);
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

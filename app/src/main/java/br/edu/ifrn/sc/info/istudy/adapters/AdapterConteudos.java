package br.edu.ifrn.sc.info.istudy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.ConteudosHolder;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConteudoClickListener;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.ConteudoWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterConteudos extends RecyclerView.Adapter<ConteudosHolder> {

    private Context mContext;

    List<Conteudo> conteudos;

    NavController navController;

    OnConteudoClickListener onConteudoClickListener;

    private String emailUsuario;

    public AdapterConteudos(Context context, List<Conteudo> conteudos, NavController navController, OnConteudoClickListener listener, String emailUsuario){
        mContext = context;
        this.conteudos = conteudos;
        this.navController = navController;
        onConteudoClickListener = listener;
        this.emailUsuario = emailUsuario;
    }

    public ConteudosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_conteudo, parent, false);
        ConteudosHolder holder = new ConteudosHolder(view, onConteudoClickListener, emailUsuario);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConteudosHolder holder, int position) {
        Conteudo tempConteudo = conteudos.get(position);
        holder.bind(tempConteudo, emailUsuario);
    }

    public void clearData() {
        conteudos.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return conteudos.size();
    }

}

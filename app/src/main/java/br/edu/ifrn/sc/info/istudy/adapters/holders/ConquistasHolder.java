package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConquistaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConquistasHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Conquista conquista;
    private ConstraintLayout clCardConquista;
    private OnConquistaClickListener onConquistaClickListener;
    private ImageView icone;
    private String email;
    private boolean desbloqueado;

    public ConquistasHolder(@NonNull View itemView, OnConquistaClickListener listener, String email) {
        super(itemView);
        clCardConquista = itemView.findViewById(R.id.card_conquista);
        clCardConquista.setOnClickListener(this);
        icone = itemView.findViewById(R.id.ivIcone);
        onConquistaClickListener = listener;
        this.email = email;
        desbloqueado = false;

    }

    public void bind(Conquista conquista) {
        this.conquista = conquista;
        setDesbloqueado();
    }

    private void setDesbloqueado(){
        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call< Integer > metodoBuscar = estudanteWS.buscarProgressoConquista(email, conquista.getId());

        metodoBuscar.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() != null && response.body() >0){
                    desbloqueado = true;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e("tste", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.card_conquista) {
            try {
                Log.d("onClick", "Bloqueado: " + desbloqueado);
                onConquistaClickListener.onConquistaClick(conquista);

            } catch (NullPointerException nullPointerException) {

            }
        }
    }
}

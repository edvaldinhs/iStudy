package br.edu.ifrn.sc.info.istudy.adapters.holders;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous.ConquistaDialog;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnConquistaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;
import br.edu.ifrn.sc.info.istudy.dominio.Estudante;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.EstudanteWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConquistasHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Activity activity;
    private Conquista conquista;
    private ConstraintLayout clCardConquista;
    private OnConquistaClickListener onConquistaClickListener;
    private ImageView icone;
    private String email;
    private boolean desbloqueado;
    private CardView cvConquista;
    private ImageView ibIcone;

    public ConquistasHolder(@NonNull View itemView, Activity activity, OnConquistaClickListener listener, String email) {
        super(itemView);
        this.activity = activity;
        clCardConquista = itemView.findViewById(R.id.card_conquista);
        clCardConquista.setOnClickListener(this);
        icone = itemView.findViewById(R.id.ivIcone);
        onConquistaClickListener = listener;
        this.email = email;
        desbloqueado = false;
        cvConquista = itemView.findViewById(R.id.cvConquista);
        ibIcone = itemView.findViewById(R.id.ibIconeConquista);

    }

    public void bind(Conquista conquista) {
        this.conquista = conquista;
        setDesbloqueado(this);
    }

    private void setDesbloqueado(View.OnClickListener listener){
        RetrofitConfig config = new RetrofitConfig();
        EstudanteWS estudanteWS = config.getEstudanteWS();
        Call< Integer > metodoBuscar = estudanteWS.buscarProgressoConquista(email, conquista.getId());

        metodoBuscar.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() != null && response.body() >0){
                    desbloqueado = true;
                }
                if (desbloqueado) {
                    int verde = ContextCompat.getColor(activity, R.color.istudy_verde_claro);
                    cvConquista.setCardBackgroundColor(verde);

                    String imageUrl = conquista.getIcone();

                    Picasso.get()
                            .load(imageUrl)
                            .into(ibIcone);
                    ibIcone.setColorFilter(ContextCompat.getColor(activity, R.color.aristofany_white), PorterDuff.Mode.SRC_IN);
                } else {
                    int cinza = ContextCompat.getColor(activity, R.color.istudy_cinza_claro);
                    cvConquista.setCardBackgroundColor(cinza);

                    String imageUrl = conquista.getIcone();

                    Picasso.get()
                            .load(imageUrl)
                            .into(ibIcone);
                    ibIcone.setColorFilter(ContextCompat.getColor(activity, R.color.istudy_cinza), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("ConquistasHolder", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.card_conquista) {
            try {
                Log.d("onClick", "desBloqueado: " + desbloqueado);
                onConquistaClickListener.onConquistaClick(conquista);
                ConquistaDialog dialog = new ConquistaDialog(activity, conquista, desbloqueado);
                dialog.iniciarConquistaDialog();
            } catch (NullPointerException nullPointerException) {

            }
        }
    }
}

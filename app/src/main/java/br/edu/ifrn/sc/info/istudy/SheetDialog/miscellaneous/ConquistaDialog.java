package br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.dominio.Conquista;

public class ConquistaDialog {

    private Activity activity;
    private AlertDialog dialog;
    private View x;
    private Conquista conquista;
    private TextView nomeConquista;
    private TextView descricao;
    private CardView cvConquista;
    private ImageButton ibIcone;
    private boolean desbloqueado;

    public ConquistaDialog(Activity activity, Conquista conquista, boolean desbloqueado){
        this.activity = activity;
        this.conquista = conquista;
        this.desbloqueado = desbloqueado;
    }

    public void iniciarConquistaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.TransparentAlertDialog);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_conquista, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();

        x = dialog.findViewById(R.id.fechar);

        nomeConquista = dialog.findViewById(R.id.tvNomeConquista);
        descricao = dialog.findViewById(R.id.tvDescricaoConquista);

        nomeConquista.setText(conquista.getNome()+"");
        descricao.setText(conquista.getDescricao()+"");

        cvConquista = dialog.findViewById(R.id.cvConquista);
        ibIcone = dialog.findViewById(R.id.ibIconeConquista);

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


        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void removerDialog(){
        dialog.dismiss();
    }

}

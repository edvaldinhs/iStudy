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
import br.edu.ifrn.sc.info.istudy.adapters.holders.QuizHolder;
import br.edu.ifrn.sc.info.istudy.dominio.Atividade;

public class AdapterQuiz extends RecyclerView.Adapter<QuizHolder> {

    private Context mContext;

    List<Atividade> atividades;

    NavController navController;

    public AdapterQuiz(Context context, List<Atividade> atividades, NavController navController){
        mContext = context;
        this.atividades = atividades;
        this.navController = navController;
    }

    public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_quiz, parent, false);
        QuizHolder holder = new QuizHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizHolder holder, int position) {
        Atividade tempAtividade = atividades.get(position);
        holder.bind(tempAtividade);
    }

    @Override
    public int getItemCount() {
        return atividades.size();
    }

}
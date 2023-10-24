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
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnQuizClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Conteudo;

public class AdapterQuiz extends RecyclerView.Adapter<QuizHolder> {

    private Context mContext;

    List<Conteudo> quizzes;

    OnQuizClickListener onQuizClickListener;

    public AdapterQuiz(Context context, List<Conteudo> quizzes, OnQuizClickListener listener){
        mContext = context;
        this.quizzes = quizzes;
        onQuizClickListener = listener;
    }

    public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_quiz, parent, false);
        QuizHolder holder = new QuizHolder(view, onQuizClickListener);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizHolder holder, int position) {
        Conteudo tempConteudo = quizzes.get(position);
        holder.bind(tempConteudo);
    }

    public void clearData() {
        quizzes.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

}

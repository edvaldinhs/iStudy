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
import br.edu.ifrn.sc.info.istudy.adapters.holders.ConteudosHolder;
import br.edu.ifrn.sc.info.istudy.adapters.holders.QuizzesByDisciplinaHolder;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnQuizzesByDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;

public class AdapterQuizzesByDisciplinas extends RecyclerView.Adapter<QuizzesByDisciplinaHolder> {

    //contexto pois será necessário num fragment
    private Context mContext;

    //ArrayList para guardar as disciplinas
    List<Disciplina> disciplinas;

    private NavController navController;

    private OnQuizzesByDisciplinaClickListener quizzesByDisciplinaClickListener;

    //Construtor
    public AdapterQuizzesByDisciplinas(Context context, List<Disciplina> disciplinas, OnQuizzesByDisciplinaClickListener listener, NavController navController){
        mContext = context;
        this.disciplinas = disciplinas;
        quizzesByDisciplinaClickListener = listener;
        this.navController = navController;
    }

    public QuizzesByDisciplinaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_quiz, parent, false);
        QuizzesByDisciplinaHolder holder = new QuizzesByDisciplinaHolder(view, quizzesByDisciplinaClickListener, navController);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizzesByDisciplinaHolder holder, int position) {
        //Cria uma disciplina temporária, pois pode haver mais de uma disciplina (tipo um for)
        Disciplina tempDisciplina = disciplinas.get(position);
        //utiliza a classe bind do QuizzesByDisciplinaHolder para modificar o que precisar
        holder.bind(tempDisciplina);
    }

    @Override
    public int getItemCount() {
        //método pra saber quantos itens existem
        return disciplinas.size();
    }

}

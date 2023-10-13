package br.edu.ifrn.sc.info.istudy.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.QuizzesByDisciplinaHolder;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;

public class AdapterQuizzesByDisciplinas extends RecyclerView.Adapter<QuizzesByDisciplinaHolder> {

    //contexto pois será necessário num fragment
    private Context mContext;

    //ArrayList para guardar as disciplinas
    List<Disciplina> disciplinas;

    //Construtor
    public AdapterQuizzesByDisciplinas(Context context, List<Disciplina> disciplinas){
        mContext = context;
        this.disciplinas = disciplinas;
    }

    @NonNull
    @Override
    public QuizzesByDisciplinaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Quando instanciar o QuizzesByDisciplinaHolder ele cria os cards
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_quiz, parent, false);
        return new QuizzesByDisciplinaHolder(view);
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

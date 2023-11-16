package br.edu.ifrn.sc.info.istudy.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.DisciplinasHolder;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;

public class AdapterDisciplinas extends RecyclerView.Adapter<DisciplinasHolder> {

    //contexto pois será necessário num fragment
    private Context mContext;

    //ArrayList para guardar as disciplinas
    List<Disciplina> disciplinas;

    private int posicaoEsquerda = -1;
    private int posicaoDireita = -1;

    private NavController navController;

    private OnDisciplinaClickListener disciplinaClickListener;

    //Construtor
    public AdapterDisciplinas(Context context, List<Disciplina> disciplinas, OnDisciplinaClickListener listener, NavController navController){
        mContext = context;
        this.disciplinas = disciplinas;
        disciplinaClickListener = listener;
        this.navController = navController;
    }

    public DisciplinasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_disciplina, parent, false);
        DisciplinasHolder holder = new DisciplinasHolder(view,mContext, disciplinaClickListener, navController);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplinasHolder holder, int position) {
        //Cria uma disciplina temporária, pois pode haver mais de uma disciplina (tipo um for)
        Disciplina tempDisciplina = disciplinas.get(position);
        //utiliza a classe bind do DisciplinasHolder para modificar o que precisar
        holder.bind(tempDisciplina);

        if(position == 0){
            holder.adicionarMargin(true);
        } else if (position == getItemCount() - 1) {
            holder.adicionarMargin(false);
        }
    }

    @Override
    public int getItemCount() {
        //método pra saber quantos itens existem
        try {
            return disciplinas.size();
        }catch(NullPointerException nullPointerException){
            return -1;
        }
    }

}

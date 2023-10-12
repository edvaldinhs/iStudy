package br.edu.ifrn.sc.info.istudy.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.adapters.holders.DisciplinasHolder;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;

public class AdapterDisciplinas extends RecyclerView.Adapter<DisciplinasHolder> {

    //contexto pois será necessário num fragment
    private Context mContext;

    //ArrayList para guardar as disciplinas
    List<Disciplina> disciplinas;

    //Construtor
    public AdapterDisciplinas(Context context, List<Disciplina> disciplinas){
        mContext = context;
        this.disciplinas = disciplinas;
    }

    @NonNull
    @Override
    public DisciplinasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Quando instanciar o DisciplinasHolder ele cria os cards
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_disciplina, parent, false);
        return new DisciplinasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplinasHolder holder, int position) {
        //Cria uma disciplina temporária, pois pode haver mais de uma disciplina (tipo um for)
        Disciplina tempDisciplina = disciplinas.get(position);
        //utiliza a classe bind do DisciplinasHolder para modificar o que precisar
        holder.bind(tempDisciplina);
    }

    @Override
    public int getItemCount() {
        //método pra saber quantos itens existem
        return disciplinas.size();
    }

}

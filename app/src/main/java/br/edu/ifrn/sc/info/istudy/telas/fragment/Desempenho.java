package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.edu.ifrn.sc.info.istudy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Desempenho#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Desempenho extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static Desempenho newInstance(String param1, String param2) {
        Desempenho fragment = new Desempenho();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Desempenho() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desempenho, container, false);

        //Deixa o botão voltar visível
        getActivity().findViewById(R.id.voltar).setVisibility(View.VISIBLE);

        return view;
    }
}
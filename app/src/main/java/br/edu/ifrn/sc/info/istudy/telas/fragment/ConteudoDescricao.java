package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.location.GnssAntennaInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import br.edu.ifrn.sc.info.istudy.R;
public class ConteudoDescricao extends Fragment {

    ImageView botaoTts;

    TextView tvDescricao;

    TextToSpeech tts;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ConteudoDescricao() {

    }

    public static ConteudoDescricao newInstance(String param1, String param2) {
        ConteudoDescricao fragment = new ConteudoDescricao();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conteudo_descricao, container, false);

        botaoTts = view.findViewById(R.id.botaoTts);

        tvDescricao = view.findViewById(R.id.descricao);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                   int linguagem = tts.setLanguage(Locale.forLanguageTag("pt-BR"));
                }
            }
        });

        botaoTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = tvDescricao.getText().toString();
//                texto = "Bom dia Daniel, tudo bom?";
                int falar = tts.speak(texto, tts.QUEUE_FLUSH, null);
            }
        });

        return view;
    }
}
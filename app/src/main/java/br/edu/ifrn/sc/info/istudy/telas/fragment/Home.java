package br.edu.ifrn.sc.info.istudy.telas.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.sc.info.istudy.R;
import br.edu.ifrn.sc.info.istudy.SheetDialog.miscellaneous.AvisoDialog;
import br.edu.ifrn.sc.info.istudy.ViewModel.SharedViewModel;
import br.edu.ifrn.sc.info.istudy.adapters.AdapterDisciplinas;
import br.edu.ifrn.sc.info.istudy.adapters.holders.click.OnDisciplinaClickListener;
import br.edu.ifrn.sc.info.istudy.dominio.Disciplina;
import br.edu.ifrn.sc.info.istudy.retrofit.RetrofitConfig;
import br.edu.ifrn.sc.info.istudy.ws.DisciplinaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment implements OnDisciplinaClickListener {

    //Cria o RecyclerView para os cards das disciplinas
    private RecyclerView rvDisciplina;

    //Armazena as disciplinas
    private List<Disciplina> disciplinas = new ArrayList<>();

    private NavController navController;

    private String nomeDisciplina;

    private String email;

    private Bundle extras;

    private AdapterDisciplinas adapterDisciplinas;

    private SharedViewModel sharedViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Home() {

    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

    //Quando criar a tela e seus componentes, esse código roda
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.frame_layout);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        //Deixa o botão voltar invisível na home
        getActivity().findViewById(R.id.voltar).setVisibility(View.INVISIBLE);

        //Diz quem é e de onde veio o RecyclerView
        rvDisciplina = view.findViewById(R.id.recyclerview_Disciplinas);

        //Usa uma classe chamada LinearLayoutManager pra organizar os cards de disciplina
        rvDisciplina.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        extras = getArguments();

        if(extras != null){
            email = extras.getString("email");
        }

        inserirIma();

        preencherDisciplinas();

        return view;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void inserirIma(){
        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvDisciplina);

        final int larguraOriginal = dpToPx(getActivity(), 200);
        final int alturaOriginal = dpToPx(getActivity(), 297);
        final int larguraFinal = dpToPx(getActivity(), 240);
        final int alturaFinal = dpToPx(getActivity(), 357);

        rvDisciplina.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (layoutManager != null) {
                    int recyclerViewCenter = recyclerView.getWidth() / 2;

                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                        View itemView = recyclerView.getChildAt(i);
                        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();

                        int itemCenter = (itemView.getLeft() + itemView.getRight()) / 2;
                        int distanceToCenter = Math.abs(itemCenter - recyclerViewCenter);

                        // Calculate the fraction based on the distance to the center
                        float fraction = 1.0f - (float) distanceToCenter / recyclerView.getWidth();

                        // Interpolate between the original size and final size based on the fraction
                        int newWidth = (int) (larguraOriginal + fraction * (larguraFinal - larguraOriginal));
                        int newHeight = (int) (alturaOriginal + fraction * (alturaFinal - alturaOriginal));

                        layoutParams.width = newWidth;
                        layoutParams.height = newHeight;
                        itemView.setLayoutParams(layoutParams);
                    }
                }
            }
        });
    }

    //Método Pra listar os cards de Disciplinas
    private void listarDisciplinas(){
        adapterDisciplinas = new AdapterDisciplinas(getActivity(), disciplinas, this, navController);
        rvDisciplina.setAdapter(adapterDisciplinas);
    }

    //Usando o Web service pra preencher a lista de disciplinas
    private void preencherDisciplinas(){
        RetrofitConfig config = new RetrofitConfig();
        DisciplinaWS disciplinaWS = config.getDisciplinaWS();
        Call<List<Disciplina>> metodoListar = disciplinaWS.listarTodas();

        metodoListar.enqueue(new Callback<List<Disciplina>>() {
            @Override
            public void onResponse(Call<List<Disciplina>> call, Response<List<Disciplina>> response) {
                disciplinas = response.body();
                listarDisciplinas();
            }

            @Override
            public void onFailure(Call<List<Disciplina>> call, Throwable t) {
                try {
                    new AvisoDialog(getActivity(), "Não foi possível carregar as disciplinas.").iniciarAvisoDialog();
                }catch(NullPointerException nullPointerException){
                    Log.e("ErroPreencher",nullPointerException.getMessage());
                }
            }
        });
    }

    @Override
    public void onDisciplinaClick(int id) {

        Bundle cartinha = new Bundle();

        cartinha.putString("email", email);

        cartinha.putInt("disciplinaId", id);

        if (navController != null) {
            navController.navigate(R.id.action_home_to_conteudo, cartinha);
            Log.e("HomeFragment", navController.toString());
        } else {
            Log.e("HomeFragment", "NavController is null");
        }
    }
}
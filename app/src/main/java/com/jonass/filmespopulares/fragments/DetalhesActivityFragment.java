package com.jonass.filmespopulares.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.jonass.filmespopulares.R;
import com.jonass.filmespopulares.adapters.ComentariosAdapter;
import com.jonass.filmespopulares.adapters.TrailerAdapter;
import com.jonass.filmespopulares.asynctask.ComentariosTask;
import com.jonass.filmespopulares.asynctask.DetalhesTaskCompleteListener;
import com.jonass.filmespopulares.asynctask.TrailersTask;
import com.jonass.filmespopulares.data.FilmeContract;
import com.jonass.filmespopulares.model.Comentario;
import com.jonass.filmespopulares.model.Filme;
import com.jonass.filmespopulares.model.Trailer;
import com.jonass.filmespopulares.util.ClickListener;
import com.jonass.filmespopulares.util.DividerItemDecoration;
import com.jonass.filmespopulares.util.RecyclerTouchListener;
import com.jonass.filmespopulares.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetalhesActivityFragment extends Fragment implements DetalhesTaskCompleteListener {

    public static final String DF_TAG = DetalhesActivityFragment.class.getSimpleName();

    private Filme filme;
    private Typeface font;
    private TextView sinopse;
    private TextView avaliacao;
    private TextView lancamento;
    private ImageView banner;
    private Button bFavorito;
    private View cDivider;
    private View tDivider;

    private ShareActionProvider cActionProvider;
    private Trailer cTrailer;

    private TrailerAdapter trailerAdapter;
    private RecyclerView tRecyclerView;
    private List<Trailer> trailerList = new ArrayList<>();

    private ComentariosAdapter comentariosAdapter;
    private RecyclerView cRecyclerView;
    private List<Comentario> comentariosList = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        if (filme != null) {
            new TrailersTask(getActivity(), this).execute(filme.getId());
            new ComentariosTask(getActivity(), this).execute(filme.getId());
        }
    }

    public DetalhesActivityFragment() {
    }

    public void carregarComponentes(View view) {
        font = Typer.set(view.getContext()).getFont(Font.ROBOTO_MEDIUM);
        sinopse = (TextView) view.findViewById(R.id.tv_sinopse);
        avaliacao = (TextView) view.findViewById(R.id.avaliacao);
        lancamento = (TextView) view.findViewById(R.id.lancamento);
        banner = (ImageView) view.findViewById(R.id.banner);
        bFavorito = (Button) view.findViewById(R.id.btn_favorito);
        tDivider = view.findViewById(R.id.below_sinopse);
        cDivider = view.findViewById(R.id.below_trailer);
        tRecyclerView = (RecyclerView) view.findViewById(R.id.rv_trailers);
        cRecyclerView = (RecyclerView) view.findViewById(R.id.rv_comentarios);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (filme != null) {
            inflater.inflate(R.menu.menu_detalhes, menu);

            MenuItem compartilhar = menu.findItem(R.id.action_compartilhar);

            cActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(compartilhar);

            if (cTrailer != null) {
                cActionProvider.setShareIntent(getIntentShare());
            }
        }
    }

    private Intent getIntentShare() {
        return new Intent(Intent.ACTION_SEND)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, filme.getTitulo() + " " +
                        "http://www.youtube.com/watch?v=" + cTrailer.getKey());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View viewRoot = inflater.inflate(R.layout.fragment_detalhes, container, false);

        carregarComponentes(viewRoot);

        Bundle bundle = getArguments();
        if (bundle != null) {
            this.filme = (Filme) bundle.getParcelable(Filme.PARCELABLE_KEY);
            ImageView capa = (ImageView) viewRoot.findViewById(R.id.capa);
            CoordinatorLayout detalhesLayout = (CoordinatorLayout) viewRoot.findViewById(R.id.content_detalhes);
            Picasso.with(viewRoot.getContext())
                    .load(filme.getCapa_path())
                    .into(capa);
            detalhesLayout.setVisibility(View.VISIBLE);
        } else {
            bundle = getActivity().getIntent().getExtras();
            if (bundle != null) {
                this.filme = (Filme) bundle.getParcelable(Filme.PARCELABLE_KEY);
            }
        }

        if (filme != null) {
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    return Util.checkFavorito(getActivity(), filme.getId());
                }

                @Override
                protected void onPostExecute(Integer isFavorito) {
                    if (isFavorito == 1) {
                        bFavorito.setText(getString(R.string.del_favoritos));
                        bFavorito.setBackgroundColor(getResources().getColor(R.color.colorAccentLight));
                    } else {
                        bFavorito.setText(getString(R.string.add_favoritos));
                        bFavorito.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                }
            }.execute();

            sinopse.setText(filme.getSinopse());
            avaliacao.setText(viewRoot.getResources().getString(R.string.comp_aval, filme.getAvaliacao()));
            lancamento.setText(Util.getData(filme.getLancamento()));
            Picasso.with(viewRoot.getContext())
                    .load(filme.getImagem_path())
                    .into(banner);
        }

        bFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Integer>() {

                    @Override
                    protected Integer doInBackground(Void... params) {
                        return Util.checkFavorito(getActivity(), filme.getId());
                    }

                    @Override
                    protected void onPostExecute(Integer favorito) {
                        if (favorito == 1) {
                            new AsyncTask<Void, Void, Integer>() {
                                @Override
                                protected Integer doInBackground(Void... params) {
                                    return getActivity().getContentResolver().delete(FilmeContract.FilmeEntry.CONTENT_URI,
                                            FilmeContract.FilmeEntry.COLUMN_FILME_ID + " = ?", new String[]{filme.getId()}
                                    );
                                }

                                @Override
                                protected void onPostExecute(Integer rowsDeleted) {
                                    bFavorito.setText(getString(R.string.add_favoritos));
                                    bFavorito.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                }
                            }.execute();
                        } else {
                            new AsyncTask<Void, Void, Uri>() {
                                @Override
                                protected Uri doInBackground(Void... params) {
                                    ContentValues values = new ContentValues();

                                    values.put(FilmeContract.FilmeEntry.COLUMN_FILME_ID, filme.getId());
                                    values.put(FilmeContract.FilmeEntry.COLUMN_TITULO, filme.getTitulo());
                                    values.put(FilmeContract.FilmeEntry.COLUMN_IMAGEM_PATH, filme.getImagem_path());
                                    values.put(FilmeContract.FilmeEntry.COLUMN_CAPA_PATH, filme.getCapa_path());
                                    values.put(FilmeContract.FilmeEntry.COLUMN_SINOPSE, filme.getSinopse());
                                    values.put(FilmeContract.FilmeEntry.COLUMN_AVALIACAO, filme.getAvaliacao());
                                    values.put(FilmeContract.FilmeEntry.COLUMN_LANCAMENTO, filme.getLancamento());

                                    return getActivity().getContentResolver().insert(FilmeContract.FilmeEntry.CONTENT_URI,
                                            values);
                                }

                                @Override
                                protected void onPostExecute(Uri returnUri) {
                                    bFavorito.setText(getString(R.string.del_favoritos));
                                    bFavorito.setBackgroundColor(getResources().getColor(R.color.colorAccentLight));
                                }
                            }.execute();
                        }
                    }
                }.execute();
            }
        });

        avaliacao.setTypeface(font);
        lancamento.setTypeface(font);

        trailerAdapter = new TrailerAdapter(trailerList);
        RecyclerView.LayoutManager tLayoutManager = new LinearLayoutManager(getActivity());
        tRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        tRecyclerView.setLayoutManager(tLayoutManager);
        tRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tRecyclerView.setAdapter(trailerAdapter);
        tRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), tRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Trailer trailer = trailerList.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                startActivity(intent);
            }
        }));

        comentariosAdapter = new ComentariosAdapter(comentariosList);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(getActivity());
        cRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        cRecyclerView.setLayoutManager(cLayoutManager);
        cRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cRecyclerView.setAdapter(comentariosAdapter);

        return viewRoot;
    }

    @Override
    public void onTaskCompleteTrailers(ArrayList<Trailer> trailers) {
        if (trailers != null) {
            if (trailers.size() > 0) {
                trailerAdapter.clear();
                tDivider.setVisibility(View.VISIBLE);

                cTrailer = trailers.get(0);
                if (cActionProvider != null) {
                    cActionProvider.setShareIntent(getIntentShare());
                }

                for (Trailer t : trailers) {
                    trailerList.add(t);
                }

                trailerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onTaskCompleteComentarios(ArrayList<Comentario> comentarios) {
        if (comentarios != null) {
            if (comentarios.size() > 0) {
                comentariosAdapter.clear();
                cDivider.setVisibility(View.VISIBLE);

                for (Comentario c : comentarios) {
                    comentariosList.add(c);
                }

                comentariosAdapter.notifyDataSetChanged();
            }
        }
    }
}
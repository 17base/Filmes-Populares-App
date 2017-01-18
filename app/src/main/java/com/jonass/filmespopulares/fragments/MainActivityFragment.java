package com.jonass.filmespopulares.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jonass.filmespopulares.R;
import com.jonass.filmespopulares.activities.SettingsActivity;
import com.jonass.filmespopulares.adapters.GaleriaAdapter;
import com.jonass.filmespopulares.asynctask.FavoritosTask;
import com.jonass.filmespopulares.asynctask.FilmesTask;
import com.jonass.filmespopulares.asynctask.FilmesTaskCompleteListener;
import com.jonass.filmespopulares.model.Filme;
import com.jonass.filmespopulares.util.Util;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivityFragment extends Fragment implements FilmesTaskCompleteListener {
    public static final String M_TAG = MainActivityFragment.class.getSimpleName();

    private GaleriaAdapter adapter;
    private GridView gridView;
    ArrayList<Filme> result = new ArrayList<Filme>();
    String pref;

    public MainActivityFragment() {
    }

    public interface Callback {
        public void onItemSelected(Filme filme);
    }

    @Override
    public void onStart() {
        super.onStart();
        atualizaFilmes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) viewRoot.findViewById(R.id.grid_view);
        adapter = new GaleriaAdapter(getActivity(), new ArrayList<Filme>());

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Filme callback = (Filme) adapter.getItem(i);
                ((Callback) getActivity()).onItemSelected(callback);
            }
        });

        return viewRoot;
    }

    private void atualizaFilmes() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref = sharedPreferences.getString(getString(R.string.pref_classificacao_key), getString(R.string.pref_classificacao_default));
        if (Util.isOnline(getContext())) {
            if (!pref.equals(getString(R.string.pref_classificacao_favoritos))) {
                new FilmesTask(getActivity(), this).execute(pref);
            } else {
                new FavoritosTask(getActivity(), this).execute();
            }
        } else {
            //Dica revisão Udacity
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getContext().getResources().getString(R.string.title_conexao))
                    .setContentText(getContext().getResources().getString(R.string.content_conexao))
                    .setCancelText(getContext().getResources().getString(R.string.dialog_cancelar))
                    .setConfirmText(getContext().getResources().getString(R.string.dialog_tentar))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            atualizaFilmes();
                        }
                    })
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onTaskCompleteFilmes(ArrayList<Filme> results) {
        if (results == null && !pref.equals(getString(R.string.pref_classificacao_favoritos))) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getContext().getResources().getString(R.string.title_erro))
                    .setContentText(getContext().getResources().getString(R.string.content_erro))
                    .setCancelText(getContext().getResources().getString(R.string.dialog_cancelar))
                    .setConfirmText(getContext().getResources().getString(R.string.dialog_tentar))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            atualizaFilmes();
                        }
                    })
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .show();
        } else if (results == null && pref.equals(getString(R.string.pref_classificacao_favoritos))) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getContext().getResources().getString(R.string.title_erro))
                    .setContentText(getContext().getResources().getString(R.string.content_erro_favoritos))
                    .setCancelText(getContext().getResources().getString(R.string.dialog_confirmar))
                    .setConfirmText(getContext().getResources().getString(R.string.dialog_alterar))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            getContext().startActivity(new Intent(getContext(), SettingsActivity.class));
                        }
                    })
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        } else {
            adapter.clear();
            result = results;

            for (Filme f : result) {
                adapter.add(f);
            }
        }
    }
}
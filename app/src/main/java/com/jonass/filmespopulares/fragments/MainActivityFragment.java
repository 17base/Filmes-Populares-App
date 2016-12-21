package com.jonass.filmespopulares.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jonass.filmespopulares.activities.DetalhesActivity;
import com.jonass.filmespopulares.adapters.GaleriaAdapter;
import com.jonass.filmespopulares.app.R;
import com.jonass.filmespopulares.asynctask.AsyncTaskCompleteListener;
import com.jonass.filmespopulares.asynctask.FilmesTask;
import com.jonass.filmespopulares.model.Filme;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AsyncTaskCompleteListener {

    final String TAG = "FILMES";
    private GaleriaAdapter adapter;
    private GridView gridView;
    ArrayList<Filme> result = new ArrayList<Filme>();

    public MainActivityFragment() {
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
                Intent intent = new Intent(getActivity(), DetalhesActivity.class);
                intent.putExtra("Info", (Filme) adapter.getItem(i));
                startActivity(intent);
            }
        });

        return viewRoot;
    }

    private void atualizaFilmes() {
        if(isOnline()){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String pref = sharedPreferences.getString(getString(R.string.pref_classificacao_key), getString(R.string.pref_classificacao_default));
            new FilmesTask(getActivity(), this).execute(pref);
        } else{
            Toast.makeText(getActivity(), "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onTaskComplete(ArrayList<Filme> results) {
        if (results == null) {
            Log.v(TAG, "Sem internet");
            /*new SweetAlertDialog(ConsultaActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Sem conexão.")
                    .show();*/
        } else {
            adapter.clear();
            result = results;

            for (Filme f:result) {
                adapter.add(f);
            }
        }
    }
}

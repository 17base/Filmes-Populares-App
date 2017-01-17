package com.jonass.filmespopulares.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.jonass.filmespopulares.R;
import com.jonass.filmespopulares.data.FilmeContract;
import com.jonass.filmespopulares.model.Filme;

import java.util.ArrayList;

/**
 * Created by JonasS on 16/12/2016.
 */

public class FavoritosTask extends AsyncTask<Void, Void, ArrayList<Filme>> {
    Context context;
    FilmesTaskCompleteListener callback;
    ProgressDialog pDialog;

    private static final String[] FILME_COLUMNS = {
            FilmeContract.FilmeEntry.TABLE_NAME + "." + FilmeContract.FilmeEntry._ID,
            FilmeContract.FilmeEntry.COLUMN_FILME_ID,
            FilmeContract.FilmeEntry.COLUMN_TITULO,
            FilmeContract.FilmeEntry.COLUMN_IMAGEM_PATH,
            FilmeContract.FilmeEntry.COLUMN_CAPA_PATH,
            FilmeContract.FilmeEntry.COLUMN_SINOPSE,
            FilmeContract.FilmeEntry.COLUMN_AVALIACAO,
            FilmeContract.FilmeEntry.COLUMN_LANCAMENTO
    };

    public static final int COL_ID = 0;
    static final int COL_FILME_ID = 1;
    static final int COL_TITULO = 2;
    static final int COL_IMAGEM_PATH = 3;
    static final int COL_CAPA_PATH = 4;
    static final int COL_SINOPSE = 5;
    static final int COL_AVALIACAO = 6;
    static final int COL_LANCAMENTO = 7;


    public FavoritosTask(Context ctx, FilmesTaskCompleteListener cb) {
        this.context = ctx;
        this.callback = cb;
        pDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.setMessage(context.getResources().getString(R.string.dialog_carregando));
        pDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Filme> results) {
        super.onPostExecute(results);
        if (pDialog != null) {
            pDialog.dismiss();
        }
        callback.onTaskCompleteFilmes(results);
    }

    @Override
    protected ArrayList<Filme> doInBackground(Void... args) {
        Cursor cursor = context.getContentResolver().query(
                FilmeContract.FilmeEntry.CONTENT_URI,
                FILME_COLUMNS,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Filme> resultFilmes = new ArrayList<Filme>();
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(COL_FILME_ID);
                String lancamento = cursor.getString(COL_LANCAMENTO);
                String avaliacao = cursor.getString(COL_AVALIACAO);
                String capa_path = cursor.getString(COL_CAPA_PATH);
                String imagem_path = cursor.getString(COL_IMAGEM_PATH);
                String titulo = cursor.getString(COL_TITULO);
                String sinopse = cursor.getString(COL_SINOPSE);

                Filme filme = new Filme(id, imagem_path, titulo, sinopse, avaliacao, lancamento, capa_path);
                resultFilmes.add(filme);

                cursor.moveToNext();
            }

            cursor.close();
            return resultFilmes;
        } else {
            return null;
        }
    }
}

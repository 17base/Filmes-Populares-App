package com.jonass.filmespopulares.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.jonass.filmespopulares.app.R;
import com.jonass.filmespopulares.model.Filme;
import com.jonass.filmespopulares.util.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.jonass.filmespopulares.util.AppConfig.APPID;
import static com.jonass.filmespopulares.util.AppConfig.BASE_IMG;
import static com.jonass.filmespopulares.util.AppConfig.IDIOMA;
import static com.jonass.filmespopulares.util.AppConfig.SIZE_BANNER;
import static com.jonass.filmespopulares.util.AppConfig.SIZE_CAPA;

/**
 * Created by JonasS on 16/12/2016.
 */

public class FilmesTask extends AsyncTask<String, Void, ArrayList<Filme>> {
    Context context;
    AsyncTaskCompleteListener callback;
    ProgressDialog pDialog;

    public FilmesTask(Context ctx, AsyncTaskCompleteListener cb) {
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
        callback.onTaskComplete(results);
    }

    @Override
    protected ArrayList<Filme> doInBackground(String... args) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String filmesJsonStr = null;

        try {
            String classificacao = (String) args[0];

            Uri.Builder b = Uri.parse(AppConfig.BASE).buildUpon().path(AppConfig.PATH).appendPath(classificacao)
                    .appendQueryParameter("api_key", APPID).appendQueryParameter("language", IDIOMA);

            URL url = new URL(b.build().toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                filmesJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                filmesJsonStr = null;
            }
            filmesJsonStr = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            filmesJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            return getFilmesJSON(filmesJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Filme> getFilmesJSON(String tempJSONStr) throws JSONException {
        final String OBJ_LIST = "results";
        final String OBJ_ID = "id";
        final String OBJ_IMAG = "poster_path";
        final String OBJ_TITL = "title";
        final String OBJ_SINP = "overview";
        final String OBJ_AVAL = "vote_average";
        final String OBJ_LANC = "release_date";
        final String OBJ_CAPA = "backdrop_path";

        ArrayList<Filme> resultFilmes = new ArrayList<Filme>();
        JSONObject baseJson = new JSONObject(tempJSONStr);
        JSONArray arrayResult = baseJson.getJSONArray(OBJ_LIST);

        for (int i = 0; i < arrayResult.length(); i++) {
            JSONObject infoFilme = arrayResult.getJSONObject(i);
            String id = infoFilme.optString(OBJ_ID);
            String iPath = infoFilme.optString(OBJ_IMAG);
            String imagem_path = BASE_IMG + SIZE_BANNER + iPath;
            String titulo = infoFilme.optString(OBJ_TITL);
            String sinopse = infoFilme.optString(OBJ_SINP);
            String avaliacao = infoFilme.optString(OBJ_AVAL);
            String lancamento = infoFilme.optString(OBJ_LANC);
            String cPath = infoFilme.optString(OBJ_CAPA);
            String capa_path = BASE_IMG + SIZE_CAPA + cPath;

            Filme filme = new Filme(id, imagem_path, titulo, sinopse, avaliacao, lancamento, capa_path);
            resultFilmes.add(filme);
        }

        return resultFilmes;
    }
}

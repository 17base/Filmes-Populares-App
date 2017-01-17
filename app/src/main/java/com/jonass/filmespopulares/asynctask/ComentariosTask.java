package com.jonass.filmespopulares.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.jonass.filmespopulares.model.Comentario;

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
import static com.jonass.filmespopulares.util.AppConfig.BASE;
import static com.jonass.filmespopulares.util.AppConfig.COMENTARIOS;
import static com.jonass.filmespopulares.util.AppConfig.IDIOMA;
import static com.jonass.filmespopulares.util.AppConfig.PATH;


/**
 * Created by JonasS on 16/12/2016.
 */


public class ComentariosTask extends AsyncTask<String, Void, ArrayList<Comentario>> {
    Context context;
    DetalhesTaskCompleteListener callback;
    ProgressDialog pDialog;

    public ComentariosTask(Context ctx, DetalhesTaskCompleteListener cb) {
        this.context = ctx;
        this.callback = cb;
        pDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Comentario> results) {
        super.onPostExecute(results);
        callback.onTaskCompleteComentarios(results);
    }

    @Override
    protected ArrayList<Comentario> doInBackground(String... args) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String comentariosJsonStr = null;

        try {
            String filmeId = (String) args[0];

            Uri.Builder b = Uri.parse(BASE).buildUpon().path(PATH).appendPath(filmeId).appendPath(COMENTARIOS)
                    .appendQueryParameter("api_key", APPID).appendQueryParameter("language", IDIOMA);

            URL url = new URL(b.build().toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                comentariosJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                comentariosJsonStr = null;
            }
            comentariosJsonStr = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            comentariosJsonStr = null;
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
            return getComentarioJSON(comentariosJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Comentario> getComentarioJSON(String tempJSONStr) throws JSONException {
        final String OBJ_LIST = "results";
        final String OBJ_ID = "id";
        final String OBJ_AUTH = "author";
        final String OBJ_CONT = "content";

        ArrayList<Comentario> resultComentarios = new ArrayList<Comentario>();
        JSONObject baseJson = new JSONObject(tempJSONStr);
        JSONArray arrayResult = baseJson.getJSONArray(OBJ_LIST);

        for (int i = 0; i < arrayResult.length(); i++) {
            JSONObject infoComentario = arrayResult.getJSONObject(i);
            String id = infoComentario.optString(OBJ_ID);
            String autor = infoComentario.optString(OBJ_AUTH);
            String conteudo = infoComentario.optString(OBJ_CONT);

            Comentario comentario = new Comentario(id, autor, conteudo);
            resultComentarios.add(comentario);
        }

        return resultComentarios;
    }
}

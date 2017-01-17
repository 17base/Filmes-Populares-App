package com.jonass.filmespopulares.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.jonass.filmespopulares.R;
import com.jonass.filmespopulares.model.Trailer;

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
import static com.jonass.filmespopulares.util.AppConfig.IDIOMA;
import static com.jonass.filmespopulares.util.AppConfig.PATH;
import static com.jonass.filmespopulares.util.AppConfig.TRAILERS;


/**
 * Created by JonasS on 16/12/2016.
 */


public class TrailersTask extends AsyncTask<String, Void, ArrayList<Trailer>> {
    Context context;
    DetalhesTaskCompleteListener callback;
    ProgressDialog pDialog;

    public TrailersTask(Context ctx, DetalhesTaskCompleteListener cb) {
        this.context = ctx;
        this.callback = cb;
        pDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Trailer> results) {
        super.onPostExecute(results);
        callback.onTaskCompleteTrailers(results);
    }

    @Override
    protected ArrayList<Trailer> doInBackground(String... args) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String trailersJsonStr = null;

        try {
            String filmeId = (String) args[0];

            Uri.Builder b = Uri.parse(BASE).buildUpon().path(PATH).appendPath(filmeId).appendPath(TRAILERS)
                    .appendQueryParameter("api_key", APPID).appendQueryParameter("language", IDIOMA);

            URL url = new URL(b.build().toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                trailersJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                trailersJsonStr = null;
            }
            trailersJsonStr = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            trailersJsonStr = null;
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
            return getTrailerJSON(trailersJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Trailer> getTrailerJSON(String tempJSONStr) throws JSONException {
        final String OBJ_LIST = "results";
        final String OBJ_ID = "id";
        final String OBJ_CHAV = "key";
        final String OBJ_SITE = "site";
        final String OBJ_TIPO = "type";

        ArrayList<Trailer> resultTrailers = new ArrayList<Trailer>();
        JSONObject baseJson = new JSONObject(tempJSONStr);
        JSONArray arrayResult = baseJson.getJSONArray(OBJ_LIST);

        for (int i = 0; i < arrayResult.length(); i++) {
            JSONObject infoTrailer = arrayResult.getJSONObject(i);
            String id = infoTrailer.optString(OBJ_ID);
            String key = infoTrailer.optString(OBJ_CHAV);
            String nome = context.getResources().getString(R.string.titulo_trailer, i + 1);
            String site = infoTrailer.optString(OBJ_SITE);
            String tipo = infoTrailer.optString(OBJ_TIPO);

            Trailer trailer = new Trailer(id, key, nome, site, tipo);
            resultTrailers.add(trailer);
        }

        return resultTrailers;
    }
}

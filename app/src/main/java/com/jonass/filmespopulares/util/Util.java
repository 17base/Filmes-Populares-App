package com.jonass.filmespopulares.util;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jonass.filmespopulares.data.FilmeContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JonasS on 22/12/2016.
 */

public class Util {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static int checkFavorito(Context context, String id) {
        Cursor cursor = context.getContentResolver().query(
                FilmeContract.FilmeEntry.CONTENT_URI,
                null, FilmeContract.FilmeEntry.COLUMN_FILME_ID + " = ?",
                new String[]{id}, null
        );
        int i = cursor.getCount();
        cursor.close();
        return i;
    }

    public static String getData(String data) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = formater.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formater.format(date);
    }
}
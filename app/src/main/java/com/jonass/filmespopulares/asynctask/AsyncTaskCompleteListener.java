package com.jonass.filmespopulares.asynctask;

import com.jonass.filmespopulares.model.Filme;

import java.util.ArrayList;

/**
 * Created by JonasS on 16/12/2016.
 */

public interface AsyncTaskCompleteListener {
    void onTaskComplete(ArrayList<Filme> filmes);
}
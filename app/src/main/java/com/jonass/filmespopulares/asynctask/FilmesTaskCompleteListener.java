package com.jonass.filmespopulares.asynctask;

import com.jonass.filmespopulares.model.Filme;

import java.util.ArrayList;

/**
 * Created by JonasS on 16/12/2016.
 */

public interface FilmesTaskCompleteListener {
    void onTaskCompleteFilmes(ArrayList<Filme> filmes);
}
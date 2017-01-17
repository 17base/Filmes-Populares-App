package com.jonass.filmespopulares.asynctask;

import com.jonass.filmespopulares.model.Comentario;
import com.jonass.filmespopulares.model.Trailer;

import java.util.ArrayList;

/**
 * Created by JonasS on 16/12/2016.
 */

public interface DetalhesTaskCompleteListener {
    void onTaskCompleteTrailers(ArrayList<Trailer> trailers);

    void onTaskCompleteComentarios(ArrayList<Comentario> comentarios);
}
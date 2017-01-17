package com.jonass.filmespopulares.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonass.filmespopulares.R;
import com.jonass.filmespopulares.model.Comentario;

import java.util.List;

/**
 * Created by jonas on 17/01/17.
 */

public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosAdapter.MyViewHolder> {
    private List<Comentario> comentariosList;
    private final Comentario comentarioX = new Comentario();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView autor;
        public TextView conteudo;

        public MyViewHolder(View view) {
            super(view);
            autor = (TextView) view.findViewById(R.id.comentario_autor);
            conteudo = (TextView) view.findViewById(R.id.comentario);
        }
    }

    public void clear() {
        synchronized (comentarioX) {
            comentariosList.clear();
        }
        notifyDataSetChanged();
    }

    public ComentariosAdapter(List<Comentario> comentariosList) {
        this.comentariosList = comentariosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_comentario, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comentario comentario = comentariosList.get(position);
        holder.autor.setText(comentario.getAutor());
        holder.conteudo.setText(comentario.getConteudo());
    }

    @Override
    public int getItemCount() {
        return comentariosList.size();
    }
}
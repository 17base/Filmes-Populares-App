package com.jonass.filmespopulares.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonass.filmespopulares.R;
import com.jonass.filmespopulares.model.Trailer;

import java.util.List;

/**
 * Created by jonas on 17/01/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    private List<Trailer> trailersList;
    private final Trailer trailerX = new Trailer();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.trailer_title);
        }
    }

    public TrailerAdapter(List<Trailer> trailersList) {
        this.trailersList = trailersList;
    }

    public void clear() {
        synchronized (trailerX) {
            trailersList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_trailer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Trailer trailer = trailersList.get(position);
        holder.title.setText(trailer.getTitulo());
    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }
}
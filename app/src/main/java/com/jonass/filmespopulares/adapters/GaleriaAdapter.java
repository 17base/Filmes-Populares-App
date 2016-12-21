package com.jonass.filmespopulares.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.jonass.filmespopulares.app.R;
import com.jonass.filmespopulares.model.Filme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JonasS on 16/12/2016.
 */

public class GaleriaAdapter extends ArrayAdapter {
    private ArrayList<Filme> imagens;
    private Context context;
    private LayoutInflater inflater;
    final String TAG = GaleriaAdapter.class.getSimpleName();

    public GaleriaAdapter(Context context, ArrayList<Filme> imagens) {
        super(context, R.layout.galeria_imagem, imagens);
        this.context = context;
        this.imagens = imagens;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.galeria_imagem, parent, false);
        }

        try{
            Picasso.with(context)
                    .load(imagens.get(position).getImagem_path())
                    .into((ImageView) convertView);
        } catch (NullPointerException e){
            Log.e(TAG, e.getMessage());
        }

        return convertView;
    }
}

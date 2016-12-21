package com.jonass.filmespopulares.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.jonass.filmespopulares.app.R;
import com.jonass.filmespopulares.model.Filme;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetalhesActivityFragment extends Fragment {

    public DetalhesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_detalhes, container, false);
        Typeface font = Typer.set(viewRoot.getContext()).getFont(Font.ROBOTO_MEDIUM);

        Intent intent = getActivity().getIntent();
        Filme filme = (Filme) intent.getSerializableExtra("Info");
        TextView sinopse = (TextView) viewRoot.findViewById(R.id.tv_sinopse);
        TextView avaliacao = (TextView) viewRoot.findViewById(R.id.avaliacao);
        TextView lancamento = (TextView) viewRoot.findViewById(R.id.lancamento);
        ImageView banner = (ImageView) viewRoot.findViewById(R.id.banner);

        sinopse.setText(filme.getSinopse());
        avaliacao.setText(viewRoot.getResources().getString(R.string.comp_aval, filme.getAvaliacao()));
        avaliacao.setTypeface(font);
        lancamento.setText(getData(filme.getLancamento()));
        lancamento.setTypeface(font);

        Picasso.with(viewRoot.getContext())
                .load(filme.getImagem_path())
                .into(banner);

        return viewRoot;
    }

    public String getData(String data) {
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
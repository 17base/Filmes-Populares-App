package com.jonass.filmespopulares.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.jonass.filmespopulares.R;
import com.jonass.filmespopulares.model.Filme;
import com.squareup.picasso.Picasso;

public class DetalhesActivity extends AppCompatActivity {
    private Filme filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        ImageView capa = (ImageView) findViewById(R.id.capa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        Typeface font = Typer.set(this).getFont(Font.ROBOTO_MEDIUM);
        collapsingToolbar.setExpandedTitleTypeface(font);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.filme = (Filme) bundle.getParcelable(Filme.PARCELABLE_KEY);
        }

        Picasso.with(this)
                .load(filme.getCapa_path())
                .into(capa);

        getSupportActionBar().setTitle(filme.getTitulo());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

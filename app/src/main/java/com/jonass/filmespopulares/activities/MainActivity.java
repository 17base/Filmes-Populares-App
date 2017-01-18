package com.jonass.filmespopulares.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jonass.filmespopulares.R;
import com.jonass.filmespopulares.fragments.DetalhesActivityFragment;
import com.jonass.filmespopulares.fragments.MainActivityFragment;
import com.jonass.filmespopulares.model.Filme;

import static com.jonass.filmespopulares.fragments.DetalhesActivityFragment.DF_TAG;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.detalhes_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detalhes_container, new DetalhesActivityFragment(),
                                DF_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_configuracoes) {
            this.startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Filme filme) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(Filme.PARCELABLE_KEY, filme);

            DetalhesActivityFragment fragment = new DetalhesActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detalhes_container, fragment, DF_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetalhesActivity.class)
                    .putExtra(Filme.PARCELABLE_KEY, filme);
            startActivity(intent);
        }
    }
}

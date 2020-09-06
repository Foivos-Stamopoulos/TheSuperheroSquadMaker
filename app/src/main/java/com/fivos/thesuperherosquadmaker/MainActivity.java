package com.fivos.thesuperherosquadmaker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fivos.thesuperherosquadmaker.ui.superHeroes.SuperHeroesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SuperHeroesFragment.newInstance())
                    .commitNow();
        }
    }

}
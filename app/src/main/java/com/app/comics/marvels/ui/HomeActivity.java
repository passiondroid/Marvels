package com.app.comics.marvels.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.app.comics.marvels.R;
import com.app.comics.marvels.ui.home.characters.CharactersFragment;
import com.app.comics.marvels.ui.home.favorites.FavoritesFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUp();
    }

    private void setUp() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
                switch (item.getItemId()) {
                    case R.id.characters:
                        if (!(currentFragment instanceof CharactersFragment)) {
                            fragment = CharactersFragment.newInstance();
                        }else {
                            fragment = currentFragment;
                        }
                        break;
                    case R.id.favorites:
                        if (!(currentFragment instanceof FavoritesFragment)) {
                            fragment = FavoritesFragment.newInstance();
                        }else {
                            fragment = currentFragment;
                        }
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "Character").commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.characters);
    }
}

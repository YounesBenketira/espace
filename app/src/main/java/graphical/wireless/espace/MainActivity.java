package graphical.wireless.espace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import graphical.wireless.espace.ui.FavouritesFragment;
import graphical.wireless.espace.ui.HomeFragment;
import graphical.wireless.espace.ui.MapFragment;
import graphical.wireless.espace.ui.NewsFragment;
import graphical.wireless.espace.ui.SearchFragment;
import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFrgmnt = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFrgmnt = new HomeFragment();
                    break;
                case R.id.nav_search:
                    selectedFrgmnt = new SearchFragment();
                    break;
                case R.id.nav_map:
                    selectedFrgmnt = new MapFragment();
                    break;
                case R.id.nav_news:
                    selectedFrgmnt = new NewsFragment();
                    break;
                case R.id.nav_favourites:
                    selectedFrgmnt = new FavouritesFragment();
                    break;
            }
            displayFragment(selectedFrgmnt);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.appbar_bottom);
        bottomNav.setOnNavigationItemSelectedListener(listener);

        displayFragment(new HomeFragment());
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}

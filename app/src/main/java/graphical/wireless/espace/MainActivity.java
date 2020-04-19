package graphical.wireless.espace;

import android.content.Intent;
import android.content.res.Resources;
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

    public PotdData[] potdDataset;
    public PlanetData[] planetDataset;
    public NewsData[] newsDataset;

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

        Resources res = getResources();

        // Create dummy data
        String[] titles = res.getStringArray(R.array.ptod_titles);
        String[] descs = res.getStringArray(R.array.ptod_desc);
        String[] dates = res.getStringArray(R.array.ptod_dates);
        int[] potdImages = new int[] {R.drawable.potd_0, R.drawable.potd_1, R.drawable.potd_2, R.drawable.potd_3, R.drawable.potd_4};

        potdDataset = new PotdData[titles.length];
        for(int i = 0; i < potdDataset.length; i++) {
            potdDataset[i] = new PotdData(titles[i], descs[i], dates[i], false, potdImages[i]);
        }

        // Planet Data
        String[] planetNames = res.getStringArray(R.array.planet_titles);
        String[] planetDesc = res.getStringArray(R.array.planet_desc);
        int[] planetImages = new int[] {R.drawable.jupiter, R.drawable.neptune, R.drawable.mars, R.drawable.saturn, R.drawable.uranus};

        planetDataset = new PlanetData[planetNames.length];
        for(int i = 0; i < planetDataset.length; i++) {
            planetDataset[i] = new PlanetData(planetNames[i], planetDesc[i], false, planetImages[i]);
        }

        // News Articles
        String[] newsHeadlines = res.getStringArray(R.array.news_headlines);
        String[] newsDesc = res.getStringArray(R.array.news_descriptions);
        int[] newsImages = new int[] {R.drawable.news0, R.drawable.news1, R.drawable.news2, R.drawable.news3, R.drawable.news4};

        newsDataset = new NewsData[newsHeadlines.length];
        for(int i = 0; i < newsDataset.length; i++) {
            newsDataset[i] = new NewsData(newsHeadlines[i], newsDesc[i], false, newsImages[i], newsDesc[i]);
        }

    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}

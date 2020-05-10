package graphical.wireless.espace;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import graphical.wireless.espace.ui.FavouritesFragment;
import graphical.wireless.espace.ui.HomeFragment;
import graphical.wireless.espace.ui.MapFragment;
import graphical.wireless.espace.ui.NewsFragment;
import graphical.wireless.espace.ui.SearchFragment;
import graphical.wireless.espace.ui.WelcomeFragment;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;

public class MainActivity extends AppCompatActivity {

    public ArrayList<PotdData> potdDataset = new ArrayList<>();
    public ArrayList<PlanetData> planetDataset = new ArrayList<>();
    public ArrayList<NewsData> newsDataset = new ArrayList<>();

    private RequestQueue requestQueue;


    private static String URL_PLANETS = "https://thawing-savannah-59760.herokuapp.com/api/v1/planet/getAllPlanets";
    private static final String URL_NEWS = "https://newsapi.org/v2/everything?language=en&q=astronomy&from=2020-05-01&sortBy=publishedAt&apiKey=53c9fbcec73142c984a917008a6bcaf1";
    private static final String TAG_RESPONSE = "JSON";

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

        // Load DataSets

        loadNewsData();
        loadPlanetData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.appbar_bottom);
        bottomNav.setOnNavigationItemSelectedListener(listener);

        displayFragment(new WelcomeFragment());

    }

    private void loadPlanetData() {
        Resources res = getResources();

        String[] planetNames = res.getStringArray(R.array.planet_titles);
        String[] planetDesc = res.getStringArray(R.array.planet_desc);
        int[] planetImages = new int[]{R.drawable.jupiter, R.drawable.neptune, R.drawable.mars, R.drawable.saturn, R.drawable.uranus};

        for (int i = 0; i < planetNames.length; i++) {
            PlanetData temp = new PlanetData(planetNames[i], planetDesc[i],"","","", planetImages[i],false);
            planetDataset.add(temp);
        }
    }

    private void loadNewsData() {
        requestQueue = Volley.newRequestQueue(this);
        // Get Today's POTD
        JsonObjectRequest newsRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_NEWS,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray articleList = (JSONArray) response.get("articles");
                            for (int i = 0; i < articleList.length(); i++) {
                                NewsData temp = NewsData.fromJson(articleList.get(i).toString());
                                newsDataset.add(temp);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i(TAG_RESPONSE, error.toString());
                    }
                });
        requestQueue.add(newsRequest);
    }

    public void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}

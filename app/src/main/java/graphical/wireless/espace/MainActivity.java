package graphical.wireless.espace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;

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
import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;
import graphical.wireless.espace.ui.data.database.Favourite;
import graphical.wireless.espace.ui.data.database.LocalDatabase;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Favourite> favourites = new ArrayList<>();

    public ArrayList<PotdData> potdDataset = new ArrayList<>();
    public ArrayList<PlanetData> planetDataset = new ArrayList<>();
    public ArrayList<NewsData> newsDataset = new ArrayList<>();

    private RequestQueue requestQueue;
    private FavouritesFragment favouritesFragment;

    private static String URL_TODAYS_POTD = "https://api.nasa.gov/planetary/apod?api_key=m9Ph2hwwzCM7HIU0dDlgJvBNxNPxf3W40hXrTia4";
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
                    selectedFrgmnt = favouritesFragment;
                    break;
            }
            displayFragment(selectedFrgmnt);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create database
        LocalDatabase.getInstance(getApplicationContext());
        LocalDatabase.getData(favourites);
        favouritesFragment = new FavouritesFragment();

        // Load DataSets
        loadPotdData();
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

    private void loadPotdData() {
        requestQueue = Volley.newRequestQueue(this);
        URL_TODAYS_POTD += "&start_date=2020-04-25";
        // Get Today's POTD
        JsonArrayRequest potdRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_TODAYS_POTD,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            ;
                            PotdData temp = null;
                            try {
                                temp = PotdData.fromJson(response.get(i).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            potdDataset.add(temp);
                        }
                        Collections.reverse(potdDataset);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i(TAG_RESPONSE, error.toString());
                    }
                });
        requestQueue.add(potdRequest);
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

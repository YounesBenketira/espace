package graphical.wireless.espace;

import android.app.ProgressDialog;
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
import testing.components.PictureOfTheDay;

public class MainActivity extends AppCompatActivity {

    public ArrayList<PotdData> potdDataset = new ArrayList<>();
    public ArrayList<PlanetData> planetDataset = new ArrayList<>();
    public ArrayList<NewsData> newsDataset = new ArrayList<>();

    public HomeFragment homeFragment = new HomeFragment();
    public SearchFragment searchFragment = new SearchFragment();
    public MapFragment mapFragment = new MapFragment();
    public NewsFragment newsFragment = new NewsFragment();
    public FavouritesFragment favouritesFragment = new FavouritesFragment();

    private RequestQueue requestQueue;

    private static final String URL_TODAYS_POTD = "https://api.nasa.gov/planetary/apod?api_key=m9Ph2hwwzCM7HIU0dDlgJvBNxNPxf3W40hXrTia4&date=2019-04-25";
    private static final String URL_NEWS = "https://newsapi.org/v2/everything?language=en&q=astronomy&from=2020-04-07&sortBy=publishedAt&apiKey=53c9fbcec73142c984a917008a6bcaf1";
    private static final String TAG_RESPONSE = "JSON";

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFrgmnt = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFrgmnt = homeFragment;
                    break;
                case R.id.nav_search:
                    selectedFrgmnt = searchFragment;
                    break;
                case R.id.nav_map:
                    selectedFrgmnt = mapFragment;
                    break;
                case R.id.nav_news:
                    selectedFrgmnt = newsFragment;
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
        loadPotdData();
        loadNewsData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.appbar_bottom);
        bottomNav.setOnNavigationItemSelectedListener(listener);
        displayFragment(new WelcomeFragment());

        Resources res = getResources();

        // Planet Data
//        String[] planetNames = res.getStringArray(R.array.planet_titles);
//        String[] planetDesc = res.getStringArray(R.array.planet_desc);
//        int[] planetImages = new int[]{R.drawable.jupiter, R.drawable.neptune, R.drawable.mars, R.drawable.saturn, R.drawable.uranus};
//
//        planetDataset = new PlanetData[planetNames.length];
//        for (int i = 0; i < planetDataset.length; i++) {
//            planetDataset[i] = new PlanetData(planetNames[i], planetDesc[i], false, planetImages[i]);
//        }

        // News Articles
//        String[] newsHeadlines = res.getStringArray(R.array.news_headlines);
//        String[] newsDesc = res.getStringArray(R.array.news_descriptions);
//        int[] newsImages = new int[]{R.drawable.news0, R.drawable.news1, R.drawable.news2, R.drawable.news3, R.drawable.news4};
//
//        newsDataset = new NewsData[newsHeadlines.length];
//        for (int i = 0; i < newsDataset.length; i++) {
//            newsDataset[i] = new NewsData(newsHeadlines[i], newsDesc[i], false, newsImages[i], newsDesc[i]);
//        }


    }

    private void loadPotdData() {
        requestQueue = Volley.newRequestQueue(this);
        // Get Today's POTD
        JsonObjectRequest potdRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_TODAYS_POTD,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        PotdData temp = PotdData.fromJson(response.toString());
                        potdDataset.add(temp);
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

    private void test() {
        String test = "https://api.nasa.gov/planetary/apod?api_key=m9Ph2hwwzCM7HIU0dDlgJvBNxNPxf3W40hXrTia4?date=20190425";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                test,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG_RESPONSE, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i(TAG_RESPONSE, error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}

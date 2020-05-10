package graphical.wireless.espace.ui.data.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import graphical.wireless.espace.ui.data.PotdData;

public class CardDataRepository {
    private static String URL_TODAYS_POTD = "https://api.nasa.gov/planetary/apod?api_key=m9Ph2hwwzCM7HIU0dDlgJvBNxNPxf3W40hXrTia4";

    private FavouriteDatabase db;

    private CardDataDao mCardDataDao;
    private LiveData<List<CardData>> mAllCardData;
    private LiveData<List<CardData>> mPotdData;

    public CardDataRepository(Application application) {
        db = FavouriteDatabase.getDatabase(application);
        mCardDataDao = db.cardDataDao();
        mAllCardData = mCardDataDao.getAll();
        mPotdData = mCardDataDao.findByDatatype(CardData.POTD_DATA);
        Log.i("Favourite", "Is it a existing mPotdData? " + (mPotdData.getValue() != null));
        if(mPotdData.getValue() == null) {
            queryPotd(application);
        }
    }

    LiveData<List<CardData>> getAllCardData() {
        return mAllCardData;
    }

    LiveData<List<CardData>> getPotdData() {
        return mPotdData;
    }

    void insert(CardData fav) {
        final CardData insertCard = fav;
        FavouriteDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mCardDataDao.insert(insertCard);
            }
        });
    }

    void queryPotd(Application application) {
        RequestQueue requestQueue = Volley.newRequestQueue(application.getApplicationContext());
        String query = URL_TODAYS_POTD + "&start_date=2020-04-25";
        // Get Today's POTD
        JsonArrayRequest potdRequest = new JsonArrayRequest(
                Request.Method.GET,
                query,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            PotdData temp = null;
                            try {
                                temp = PotdData.fromJson(response.get(i).toString());
                                Log.i("Favourite", "Making potddata from json req");
                                insert(CardData.fromData(temp));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("CardDataRepository", error.toString());
                    }
                });
        requestQueue.add(potdRequest);
    }

}

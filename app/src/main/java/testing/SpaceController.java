package testing;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import testing.components.PictureOfTheDay;

public class SpaceController extends Application {
    private Context context;
    private RequestQueue requestQueue;
    private static final String URL_TODAYS_POTD = "https://api.nasa.gov/planetary/apod?api_key=m9Ph2hwwzCM7HIU0dDlgJvBNxNPxf3W40hXrTia4";
    private static final String TAG_RESPONSE = "JSON";

    //@TODO Find a way to get Context from within the testing package
    public SpaceController(Context context) {
        this.context = context;
    }

    public void testPOTD() {

        requestQueue = Volley.newRequestQueue(context);

        // Get Today's POTD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_TODAYS_POTD,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        PictureOfTheDay pictureOfDay = PictureOfTheDay.fromJson(response.toString());
                        //Log.i(TAG_RESPONSE, response.toString());
                        //Log.i(TAG_RESPONSE, pictureOfDay.toString());
                        System.out.println(pictureOfDay.toString());
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


    public void testSearchPOTD() {

    }

}

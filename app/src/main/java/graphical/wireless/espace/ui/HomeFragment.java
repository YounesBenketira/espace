package graphical.wireless.espace.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import graphical.wireless.espace.R;
import graphical.wireless.espace.components.PictureOfTheDay;


/*
    Your API key for rpnf@hotmail.com is:

    m9Ph2hwwzCM7HIU0dDlgJvBNxNPxf3W40hXrTia4
    You can start using this key to make web service requests. Simply pass your key in the URL when making a web request. Here's an example:

    https://api.nasa.gov/planetary/apod?api_key=m9Ph2hwwzCM7HIU0dDlgJvBNxNPxf3W40hXrTia4
    For additional support, please contact us. When contacting us, please tell us what API you're accessing and provide the following account details so we can quickly find you:

    Account Email: rpnf@hotmail.com
    Account ID: ec8b20ce-0c04-4605-a229-7e1498575945
 */
public class HomeFragment extends Fragment {
    private RequestQueue requestQueue;
    private static final String URL_TODAYS_POTD = "https://api.nasa.gov/planetary/apod?api_key=m9Ph2hwwzCM7HIU0dDlgJvBNxNPxf3W40hXrTia4";
    private static final String TAG_RESPONSE = "JSON";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Using Volley Library
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // Get Today's POTD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_TODAYS_POTD,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG_RESPONSE, error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void parseJSON(JSONObject response) {
        PictureOfTheDay pictureOfDay = PictureOfTheDay.fromJson(response.toString());
        //Log.i(TAG_RESPONSE, response.toString());
        //Log.i(TAG_RESPONSE, pictureOfDay.toString());
    }

}

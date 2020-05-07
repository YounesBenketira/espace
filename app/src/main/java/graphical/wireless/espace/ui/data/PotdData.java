package graphical.wireless.espace.ui.data;

import android.util.Log;

import com.google.gson.Gson;

public class PotdData extends EspaceData {
    // The property names must be the same as the corresponding label in the JSON string
    private String copyright;
    private String date;
    private String explanation;
    private String media_type;
    private String title;
    private String url;
    private String hdurl;
    private String service_version;

    /**
     * Using Gson library to parse the JSON string into the POTD properties
     *
     * @param response is the JSON object in spring representation
     * @return Picture-Of-The-Day Object (POTD)
     */
    public static PotdData fromJson(String response) {
        PotdData temp = new Gson().fromJson(response, PotdData.class);
//        Log.i("TEST", "fromJson: "+ temp.title);
        return new PotdData(temp.title, temp.explanation, temp.copyright, temp.date, temp.url, -1, false);
    }

    public PotdData(String title, String description, String author, String date, String imageURL, int imageID, boolean isFavourite) {
        super(title, description, author, date, imageURL, imageID, isFavourite);
    }

    /**
     * Convert current Object into a JSON string representation
     *
     * @return JSON formatted String of current Object
     */
    public String toString() {
        return new Gson().toJson(this);
    }
}

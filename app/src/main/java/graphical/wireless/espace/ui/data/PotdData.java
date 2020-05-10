package graphical.wireless.espace.ui.data;

import androidx.cardview.widget.CardView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import java.util.concurrent.Future;

import javax.annotation.Nullable;

import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.components.Cardable;
import graphical.wireless.espace.ui.data.database.FavouriteDatabase;

public class PotdData extends EspaceData implements Cardable {
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

        // Async call for checking if the title exists
        ListenableFuture<Boolean> possibleFavourite = FavouriteDatabase.doesTitleExist(temp.title);

        final PotdData data = new PotdData(temp.title, temp.explanation, temp.copyright, temp.date, temp.url, -1, false);

        // Add callback on completion of the async call
        Futures.addCallback(possibleFavourite, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean result) {
                if(result != null && result) {
                    data.setFavourite(true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                android.util.Log.i("Thread", "Callback failure" + t.getMessage());
            }
        }, FavouriteDatabase.service);

        return data;
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

    @Override
    public CardView getCardView(CardView card) {
        return super.getCardView(card);
    }
}

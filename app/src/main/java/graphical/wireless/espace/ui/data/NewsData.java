package graphical.wireless.espace.ui.data;

import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Map;

import javax.annotation.Nullable;

import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.components.Cardable;
import graphical.wireless.espace.ui.data.database.FavouriteDatabase;

public class NewsData extends EspaceData implements Cardable {
    private Map<String, String> source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    public static NewsData fromJson(String response) {
        NewsData temp = new Gson().fromJson(response, NewsData.class);

        // Async call for checking if the title exists
        ListenableFuture<Boolean> possibleFavourite = FavouriteDatabase.doesTitleExist(temp.title);

        final NewsData data = new NewsData(temp.title, temp.description, temp.author, temp.publishedAt, temp.urlToImage, -1, false);

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

    public NewsData(String title, String description, String author, String date, String imageURL, int imageID, boolean isFavourite) {
        super(title, description, author, date, imageURL, imageID, isFavourite);
    }

    public CardView setCardView(CardView card) {
        ((TextView)card.findViewById(R.id.card_title)).setText(getTitleText());
        ((TextView)card.findViewById(R.id.card_date)).setText(getDateText());

        ImageView imageView = card.findViewById(R.id.card_image);

        if (getImageURL() == null || getImageURL().length() == 0 || getImageURL().charAt(4) != 's')
            imageView.setImageResource(R.drawable.noimage);
        else {
            Picasso.get().load(getImageURL()).into(imageView);
        }

        return card;
    }
}

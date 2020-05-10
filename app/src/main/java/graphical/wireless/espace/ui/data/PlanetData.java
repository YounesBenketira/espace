package graphical.wireless.espace.ui.data;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.components.Cardable;

public class PlanetData extends EspaceData implements Cardable {

    // Constructors
    public PlanetData(String title, String description, String author, String date, String imageURL, int imageID, boolean isFavourite) {
        super(title, description, author, date, null, imageID, isFavourite);
    }

    @Override
    public CardView getCardView(CardView card) {
        ((TextView)card.findViewById(R.id.card_title)).setText(getTitleText());
        ((TextView)card.findViewById(R.id.card_date)).setText(getDateText());

        ImageView imageView = card.findViewById(R.id.card_image);
        imageView.setImageResource(getImageID());

        return card;
    }
}

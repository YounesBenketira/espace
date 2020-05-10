package graphical.wireless.espace.ui.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.common.util.concurrent.ListenableFuture;
import com.squareup.picasso.Picasso;

import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.components.Cardable;
import graphical.wireless.espace.ui.components.FavouriteButton;
import graphical.wireless.espace.ui.data.database.Favourite;
import graphical.wireless.espace.ui.data.database.FavouriteDatabase;

public class EspaceData implements Parcelable, Cardable {

    // Parcel Creator
    public static final Creator<EspaceData> CREATOR = new Creator<EspaceData>() {
        @Override
        public EspaceData createFromParcel(Parcel source) {
            return new EspaceData(source);
        }

        @Override
        public EspaceData[] newArray(int size) {
            return new EspaceData[size];
        }
    };

    // Fields
    private String titleText;
    private String descriptionText;
    private String authorText;
    private String dateText;
    private String imageURL;
    private int imageID;

    private boolean isFavourite;

    // Constructors
    public EspaceData(String title, String description, String author, String date, String imageURL, int imageID, boolean isFavourite) {
        this.titleText = title;
        this.descriptionText = description;
        this.authorText = author;
        this.dateText = date;

        if (imageID == -1) {
            this.imageURL = imageURL;
        } else {
            this.imageID = imageID;
        }

        this.isFavourite = isFavourite;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getAuthorText() {
        return authorText;
    }

    public void setAuthorText(String authorText) {
        this.authorText = authorText;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    private EspaceData(Parcel in) {
        titleText = in.readString();
        descriptionText = in.readString();
        authorText = in.readString();
        dateText = in.readString();

        if (this.imageURL == null)
            imageID = in.readInt();
        else
            imageURL = in.readString();

        isFavourite = in.readByte() == 1;
    }

    EspaceData() {

    }

    // Accessors


    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    // Parcelable Methods
    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titleText);
        dest.writeString(descriptionText);
        dest.writeString(authorText);
        dest.writeString(dateText);


        dest.writeString(imageURL);
        dest.writeInt(imageID);

        dest.writeByte(isFavourite ? (byte) 1 : (byte) 0);
    }

    @Override
    public CardView getCardView(CardView card) {
        ((TextView)card.findViewById(R.id.card_title)).setText(getTitleText());
        ((TextView)card.findViewById(R.id.card_date)).setText(getDateText());

        ImageView imageView = card.findViewById(R.id.card_image);
        if(getImageURL().charAt(8) != 'w')
            Picasso.get().load(getImageURL()).into(imageView);
        else
            imageView.setImageResource(R.drawable.noimage);

        final EspaceData data = this;
        FavouriteButton fb = card.findViewById(R.id.card_favourite_button);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((FavouriteButton)v).isChecked()) {
                    FavouriteDatabase.insert(Favourite.fromData(data));
                } else {
                    FavouriteDatabase.delete(Favourite.fromData(data));
                }
            }
        });

        return card;
    }
}

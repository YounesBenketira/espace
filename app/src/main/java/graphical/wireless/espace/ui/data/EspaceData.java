package graphical.wireless.espace.ui.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class EspaceData implements Parcelable {

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
    EspaceData(String title, String description, String author, String date, String imageURL, int imageID, boolean isFavourite) {
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
}

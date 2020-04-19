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
    private String mainText;
    private String auxText;
    private String dateText;

    private boolean isFavourite;

    private int imageId;

    // Constructors
    EspaceData(String mainText, String auxText, String dateText, boolean isFavourite, int imageId) {
        this.mainText = mainText;
        this.auxText = auxText;
        this.dateText = dateText;
        this.isFavourite = isFavourite;
        this.imageId = imageId;
    }

    private EspaceData(Parcel in) {
        mainText = in.readString();
        auxText = in.readString();
        dateText = in.readString();

        isFavourite = in.readByte() == 1;

        imageId = in.readInt();
    }

    EspaceData() {

    }

    // Accessors
    public String getMainText() {
        return mainText;
    }

    public String getAuxText() {
        return auxText;
    }

    public String getDateText() {
        return dateText;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public int getImageId() {
        return imageId;
    }

    // Mutators
    public void setMainText(String text) {
        mainText = text;
    }

    public void setAuxText(String text) {
        auxText = text;
    }

    public void setDateText(String text) {
        dateText = text;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public void setImageId(int id) {
        imageId = id;
    }

    // Parcelable Methods
    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mainText);
        dest.writeString(auxText);
        dest.writeString(dateText);
        dest.writeByte(isFavourite ? (byte)1 : (byte)0);
        dest.writeInt(imageId);
    }
}

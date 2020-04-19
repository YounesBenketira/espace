package graphical.wireless.espace.ui.data;

import android.os.Parcelable;

public class NewsData extends EspaceData implements Parcelable {

    // Fields
    private String desc;

    // Constructors
    public NewsData(String headline, String author, boolean isFavourite, int image, String desc) {
        super(headline, author, "", isFavourite, image);

        this.desc = desc;
    }

    // Accessors
    public String getHeadline() {
        return this.getMainText();
    }

    public String getAuthor() {
        return this.getAuxText();
    }

    public String getDesc() {
        return desc;
    }

    // Mutators
    public void setHeadline(String headline) {
        this.setMainText(headline);
    }

    public void setAuthor(String author) {
        this.setAuxText(author);
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

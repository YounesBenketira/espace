package graphical.wireless.espace.ui.data;

import android.graphics.drawable.Drawable;

public class PotdData extends EspaceData {

    // Constructors
    public PotdData(String name, String desc, String date, boolean isFavourite, int image) {
        super(name, desc, date, isFavourite, image);
    }

    // Accessors
    public String getName() {
        return this.getMainText();
    }

    public String getDesc() {
        return this.getAuxText();
    }

    // Mutators
    public void setName(String name) {
        this.setMainText(name);
    }

    public void setDesc(String desc) {
        this.setAuxText(desc);
    }
}

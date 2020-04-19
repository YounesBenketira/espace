package graphical.wireless.espace.ui.data;

import android.graphics.drawable.Drawable;

public class PlanetData extends EspaceData {

    // Constructors
    public PlanetData(String name, String desc, boolean isFavourite, int image) {
        super(name, desc, "", isFavourite, image);
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

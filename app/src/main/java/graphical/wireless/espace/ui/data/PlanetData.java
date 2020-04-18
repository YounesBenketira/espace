package graphical.wireless.espace.ui.data;

public class PlanetData extends EspaceData{
    String name;
    String desc;
    String imageName;

    public PlanetData(String name, String desc, String image) {
        this.name = name;
        this.desc = desc;
        this.imageName = image;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return imageName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.imageName = image;
    }
}

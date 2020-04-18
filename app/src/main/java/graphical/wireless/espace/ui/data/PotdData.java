package graphical.wireless.espace.ui.data;

public class PotdData extends EspaceData {
    String date;
    String imageName;
    String desc;

    public PotdData(String date, String imageName, String desc) {
        this.date = date;
        this.imageName = imageName;
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public String getImageName() {
        return imageName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

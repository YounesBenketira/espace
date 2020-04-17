package graphical.wireless.espace.ui.data;

public class NewsData {
    String headLine;
    String author;
    String imageName;
    String desc;

    public NewsData(String headline, String author, String imageName, String desc) {
        this.headLine = headline;
        this.author = author;
        this.imageName = imageName;
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public String getDesc() {
        return desc;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getImageName() {
        return imageName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}

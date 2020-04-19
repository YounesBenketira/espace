package graphical.wireless.espace.ui.data;

public class NewsData extends EspaceData {

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

    // Mutators
    public void setHeadline(String headline) {
        this.setMainText(headline);
    }

    public void setAuthor(String author) {
        this.setAuxText(author);
    }
}

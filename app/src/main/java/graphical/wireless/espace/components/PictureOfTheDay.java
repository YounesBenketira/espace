package graphical.wireless.espace.components;

import com.google.gson.Gson;

public class PictureOfTheDay {
    // The property names must be the same as the corresponding label in the JSON string
    private String copyright;
    private String date;
    private String explanation;
    private String media_type;
    private String title;
    private String url;
    private String hdurl;
    //private String service_version;

    /**
     * Using Gson library to parse the JSON string into the POTD properties
     *
     * @param response is the JSON object in spring representation
     * @return Picture-Of-The-Day Object (POTD)
     */
    public static PictureOfTheDay fromJson(String response) {
        return new Gson().fromJson(response, PictureOfTheDay.class);
    }

    /**
     * Convert current Object into a JSON string representation
     *
     * @return JSON formatted String of current Object
     */
    public String toString() {
        return new Gson().toJson(this);
    }

    // Getters & Setters
    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }
}

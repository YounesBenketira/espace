package graphical.wireless.espace.ui.data;

import com.google.gson.Gson;

import java.util.Map;

public class NewsData extends EspaceData {
    private Map<String, String> source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    public static NewsData fromJson(String response) {
        NewsData temp = new Gson().fromJson(response, NewsData.class);

        return temp;
    }

    public NewsData(String title, String description, String author, String date, String imageURL, int imageID, boolean isFavourite) {
        super(title, description, author, date, imageURL, imageID, isFavourite);
    }
}

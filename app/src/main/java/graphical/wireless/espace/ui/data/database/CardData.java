package graphical.wireless.espace.ui.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;

@Entity
public class CardData {
    public static final int POTD_DATA = 0;
    public static final int PLANET_DATA = 1;
    public static final int NEWS_DATA = 2;
    public static final int ESPACE_DATA = 3;

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "espace_data")
    public int espaceDatatype;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "image_url")
    public String imageURL;

    @ColumnInfo(name = "image_id")
    public int imageID;

    @ColumnInfo(name = "is_favourite")
    public boolean isFavourite;

    public EspaceData getData() {
        switch(espaceDatatype) {
            case POTD_DATA:
                return new PotdData(title, description,author,date,imageURL,imageID,isFavourite);
            case PLANET_DATA:
                return new PlanetData(title, description, author,date,imageURL,imageID,isFavourite);
            case NEWS_DATA:
                return new NewsData(title, description, author, date, imageURL,imageID,isFavourite);
            case ESPACE_DATA:
            default:
                return new EspaceData(title, description, author, date,imageURL,imageID, isFavourite);
        }
    }

    public static CardData fromData(EspaceData data) {
        CardData fav = new CardData();
        fav.title = data.getTitleText();
        fav.description = data.getDescriptionText();
        fav.author = data.getAuthorText();
        fav.date = data.getDateText();
        fav.imageURL = data.getImageURL();
        fav.imageID = data.getImageID();
        fav.isFavourite = data.isFavourite();

        int datatype = CardData.ESPACE_DATA;
        if(data instanceof PotdData) {
            datatype = CardData.POTD_DATA;
        } else if(data instanceof PlanetData) {
            datatype = CardData.PLANET_DATA;
        } else if(data instanceof NewsData) {
            datatype = CardData.NEWS_DATA;
        }

        fav.espaceDatatype = datatype;

        return fav;
    }

    @Override
    public String toString() {
        return String.format("{title?%s, desc?%s, auth?%s, date?%s, url?%s, id?%d, fav?%b}", title, description, author, date, imageURL, imageID, isFavourite);
    }
}

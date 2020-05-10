package graphical.wireless.espace.ui.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;

@Entity
public class Favourite {
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

    public EspaceData getData() {
        switch(espaceDatatype) {
            case POTD_DATA:
                return new PotdData(title, description,author,date,imageURL,imageID,true);
            case PLANET_DATA:
                return new PlanetData(title, description, author,date,imageURL,imageID,true);
            case NEWS_DATA:
                return new NewsData(title, description, author, date, imageURL,imageID,true);
            case ESPACE_DATA:
            default:
                return new EspaceData(title, description, author, date,imageURL,imageID, true);
        }
    }

    public static Favourite fromData(EspaceData data) {
        Favourite fav = new Favourite();
        fav.title = data.getTitleText();
        fav.description = data.getDescriptionText();
        fav.author = data.getAuthorText();
        fav.date = data.getDateText();
        fav.imageURL = data.getImageURL();
        fav.imageID = data.getImageID();

        int datatype = Favourite.ESPACE_DATA;
        if(data instanceof PotdData) {
            datatype = Favourite.POTD_DATA;
        } else if(data instanceof PlanetData) {
            datatype = Favourite.PLANET_DATA;
        } else if(data instanceof NewsData) {
            datatype = Favourite.NEWS_DATA;
        }

        fav.espaceDatatype = datatype;

        return fav;
    }
}

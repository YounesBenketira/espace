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
    public static final int DATA_POTD = 0;
    public static final int DATA_PLANET = 1;
    public static final int DATA_NEWS = 2;
    public static final int DATA_ESPACE = 3;
    public static int favouriteCount = 0;

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

    public Favourite(int espaceDatatype, String title, String description, String author, String date, String imageURL, int imageID) {
        this.uid = favouriteCount++;
        this.espaceDatatype = espaceDatatype;
        this.title = title;
        this.description = description;
        this.author = author;
        this.date = date;
        this.imageURL = imageURL;
        this.imageID = imageID;
    }

    public EspaceData toEspaceData() {
        switch(espaceDatatype) {
            case DATA_POTD: return new PotdData(title, description, author, date, imageURL, imageID, true);
            case DATA_PLANET: return new PlanetData(title, description, author, date, imageURL, imageID, true);
            case DATA_NEWS: return new NewsData(title, description, author, date, imageURL, imageID, true);
            case DATA_ESPACE:
            default: return new EspaceData(title, description, author, date, imageURL, imageID, true);
        }
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "uid=" + uid +
                ", espaceDatatype=" + espaceDatatype +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", imageID=" + imageID +
                "} ";
    }
}

package graphical.wireless.espace.ui.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import graphical.wireless.espace.ui.data.EspaceData;

@Entity
public class Favourite {
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
}

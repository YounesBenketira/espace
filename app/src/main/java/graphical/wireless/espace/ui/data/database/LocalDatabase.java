package graphical.wireless.espace.ui.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favourite.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract FavouriteDao getFavouriteDao();
}
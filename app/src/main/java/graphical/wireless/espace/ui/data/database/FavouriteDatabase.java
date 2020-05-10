package graphical.wireless.espace.ui.data.database;

import android.content.Context;

import androidx.room.Room;

import java.io.IOException;
import java.util.List;

public class FavouriteDatabase {

    // Cannot be constructed from outside, this is a static class
    private FavouriteDatabase() {

    }

    protected static LocalDatabase db;
    protected static FavouriteDao favouriteDao;

    public static void createDatabase(Context context) {
        if(db == null) {
            db = Room.databaseBuilder(context, LocalDatabase.class, "favourites").build();

            favouriteDao = db.getFavouriteDao();
        }
    }

    public static void closeDatabase() throws IOException {
        if(db != null) {
            favouriteDao = null;
            db.close();
        }
    }

    public static List<Favourite> getAll() {
        return favouriteDao.getAll();
    }

    public static List<Favourite> loadAllByIds(int[] userIds) {
        return favouriteDao.loadAllByIds((userIds));
    }

    public static Favourite findByTitle(String title) {
        return favouriteDao.findByTitle(title);
    }

    public static int countFavouritesByTitle(String title) {
        return favouriteDao.countFavouritesByTitle(title);
    }

    public static boolean doesTitleExist(String title) {
        return countFavouritesByTitle(title) > 0;
    }

    public static void insertAll(List<Favourite> list) {
        for(int i = 0; i < list.size(); i++) {
            insert(list.get(i));
        }
    }

    /**
     *
     * @param favourite
     * @return true, if and only if the entry existed before insertion, otherwise false
     */
    public static boolean insert(Favourite favourite) {
        if(doesTitleExist(favourite.title)) {
            favouriteDao.updateFavourite(favourite);
            return true; // Already existed and updated it
        } else {
            favouriteDao.insert(favourite);
            return false; // Did not exist, but it does now
        }
    }
}

package graphical.wireless.espace.ui.data.database;

import android.content.Context;

import androidx.room.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;

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

    public static List<Favourite> getAllFavourites() {
        return favouriteDao.getAll();
    }

    public static List<PotdData> getPotdFavourites() {
        List<Favourite> potdFavData = favouriteDao.findByDatatype(Favourite.POTD_DATA);
        List<PotdData> list = new ArrayList<>(potdFavData.size());
        for(int i = 0; i < potdFavData.size(); i++) {
            Favourite f = potdFavData.get(i);
            list.add((PotdData)f.getData());
        }

        return list;
    }

    public static List<PlanetData> getPlanetFavourites() {
        List<Favourite> potdFavData = favouriteDao.findByDatatype(Favourite.PLANET_DATA);
        List<PlanetData> list = new ArrayList<>(potdFavData.size());
        for(int i = 0; i < potdFavData.size(); i++) {
            Favourite f = potdFavData.get(i);
            list.add((PlanetData)f.getData());
        }

        return list;
    }

    public static List<NewsData> getNewsFavourites() {
        List<Favourite> potdFavData = favouriteDao.findByDatatype(Favourite.NEWS_DATA);
        List<NewsData> list = new ArrayList<>(potdFavData.size());
        for(int i = 0; i < potdFavData.size(); i++) {
            Favourite f = potdFavData.get(i);
            list.add((NewsData)f.getData());
        }

        return list;
    }

    public static List<EspaceData> getEspaceFavourites() {
        List<Favourite> potdFavData = favouriteDao.findByDatatype(Favourite.ESPACE_DATA);
        List<EspaceData> list = new ArrayList<>(potdFavData.size());
        for(int i = 0; i < potdFavData.size(); i++) {
            Favourite f = potdFavData.get(i);
            list.add(f.getData());
        }

        return list;
    }

    public static List<Favourite> loadAllByIds(int[] userIds) {
        return favouriteDao.loadAllByIds((userIds));
    }

    public static Favourite findByTitle(String title) {
        return favouriteDao.findByTitle(title);
    }

    public static void delete(Favourite favourite) {
        favouriteDao.delete(favourite);
    }

    public static int countFavouritesByTitle(String title) {
        return favouriteDao.countFavouritesByTitle(title);
    }

    public static PotdData getPotdByTitle(String title) {
        return (PotdData)favouriteDao.findByTitleData(title, Favourite.POTD_DATA).getData();
    }

    public static PlanetData getPlanetByTitle(String title) {
        return (PlanetData) favouriteDao.findByTitleData(title, Favourite.PLANET_DATA).getData();
    }

    public static NewsData getNewsByTitle(String title) {
        return (NewsData) favouriteDao.findByTitleData(title, Favourite.NEWS_DATA).getData();
    }

    public static EspaceData getEspaceByTitle(String title) {
        return favouriteDao.findByTitleData(title, Favourite.ESPACE_DATA).getData();
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

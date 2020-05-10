package graphical.wireless.espace.ui.data.database;

import android.content.Context;

import androidx.room.Room;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;

public class FavouriteDatabase {

    public final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(16));

    // Cannot be constructed from outside, this is a static class
    private FavouriteDatabase() {

    }

    protected static LocalDatabase db;
    protected static FavouriteDao favouriteDao;

    public static void createDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, LocalDatabase.class, "favourites").build();

            favouriteDao = db.getFavouriteDao();
        }
    }

    public static void closeDatabase() throws IOException {
        if (db != null) {
            favouriteDao = null;
            db.close();
        }
    }

    public static ListenableFuture<List<Favourite>> getAllFavourites() {
        return service.submit(new Callable<List<Favourite>>() {
            @Override
            public List<Favourite> call() throws Exception {
                return favouriteDao.getAll();
            }
        });
    }

    public static ListenableFuture<List<PotdData>> getPotdFavourites() {

        return service.submit(new Callable<List<PotdData>>() {
            @Override
            public List<PotdData> call() throws Exception {
                List<Favourite> potdFavData = favouriteDao.findByDatatype(Favourite.POTD_DATA);
                List<PotdData> list = new ArrayList<>(potdFavData.size());
                for (int i = 0; i < potdFavData.size(); i++) {
                    Favourite f = potdFavData.get(i);
                    list.add((PotdData) f.getData());
                }

                return list;
            }
        });

    }

    public static ListenableFuture<List<PlanetData>> getPlanetFavourites() {
        return service.submit(new Callable<List<PlanetData>>() {
            @Override
            public List<PlanetData> call() throws Exception {
                List<Favourite> planetFavData = favouriteDao.findByDatatype(Favourite.PLANET_DATA);
                List<PlanetData> list = new ArrayList<>(planetFavData.size());
                for (int i = 0; i < planetFavData.size(); i++) {
                    Favourite f = planetFavData.get(i);
                    list.add((PlanetData) f.getData());
                }

                return list;
            }
        });
    }

    public static ListenableFuture<List<NewsData>> getNewsFavourites() {
        return service.submit(new Callable<List<NewsData>>() {
            @Override
            public List<NewsData> call() throws Exception {
                List<Favourite> newsFavData = favouriteDao.findByDatatype(Favourite.NEWS_DATA);
                List<NewsData> list = new ArrayList<>(newsFavData.size());
                for (int i = 0; i < newsFavData.size(); i++) {
                    Favourite f = newsFavData.get(i);
                    list.add((NewsData) f.getData());
                }

                return list;
            }
        });
    }

    public static ListenableFuture<List<EspaceData>> getEspaceFavourites() {
        return service.submit(new Callable<List<EspaceData>>() {
            @Override
            public List<EspaceData> call() throws Exception {
                List<Favourite> espaceFavData = favouriteDao.getAll();
                List<EspaceData> list = new ArrayList<>(espaceFavData.size());
                for (int i = 0; i < espaceFavData.size(); i++) {
                    Favourite f = espaceFavData.get(i);
                    list.add(f.getData());
                }

                return list;
            }
        });
    }

    public static ListenableFuture<List<Favourite>> loadAllByIds(int[] userIds) {
        final int[] ids = userIds;
        return service.submit(new Callable<List<Favourite>>() {
            @Override
            public List<Favourite> call() throws Exception {
                return favouriteDao.loadAllByIds((ids));
            }
        });
    }

    public static ListenableFuture<Favourite> findByTitle(String title) {
        final String t = title;
        return service.submit(new Callable<Favourite>() {
            @Override
            public Favourite call() throws Exception {
                return favouriteDao.findByTitle(t);
            }
        });
    }

    public static ListenableFuture<Void> delete(Favourite favourite) {
        final Favourite fav = favourite;
        return service.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                favouriteDao.delete(fav);
                return null;
            }
        });
    }

    public static ListenableFuture<Integer> countFavouritesByTitle(String title) {
        final String t = title;
        return service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return favouriteDao.countFavouritesByTitle(t);
            }
        });
    }

    public static ListenableFuture<PotdData> getPotdByTitle(String title) {
        final String t = title;
        return service.submit(new Callable<PotdData>() {
            @Override
            public PotdData call() throws Exception {
                return (PotdData) favouriteDao.findByTitleData(t, Favourite.POTD_DATA).getData();
            }
        });
    }

    public static ListenableFuture<PlanetData> getPlanetByTitle(String title) {
        final String t = title;
        return service.submit(new Callable<PlanetData>() {
            @Override
            public PlanetData call() throws Exception {
                return (PlanetData) favouriteDao.findByTitleData(t, Favourite.PLANET_DATA).getData();
            }
        });
    }

    public static ListenableFuture<NewsData> getNewsByTitle(String title) {
        final String t = title;
        return service.submit(new Callable<NewsData>() {
            @Override
            public NewsData call() throws Exception {
                return (NewsData) favouriteDao.findByTitleData(t, Favourite.NEWS_DATA).getData();
            }
        });
    }

    public static ListenableFuture<EspaceData> getEspaceByTitle(String title) {
        final String t = title;
        return service.submit(new Callable<EspaceData>() {
            @Override
            public EspaceData call() throws Exception {
                return favouriteDao.findByTitle(t).getData();
            }
        });
    }

    public static ListenableFuture<Boolean> doesTitleExist(String title) {
        final String t = title;
        return service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Future<Integer> count = countFavouritesByTitle(t);

                return countFavouritesByTitle(t).get() > 0;
            }
        });
    }

    public static ListenableFuture<Void> insertAll(List<Favourite> list) {
        final List<Favourite> l = list;
        return service.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (int i = 0; i < l.size(); i++) {
                    insert(l.get(i));
                }
                return null;
            }
        });

    }

    /**
     * @param favourite
     * @return true, if and only if the entry existed before insertion, otherwise false
     */
    public static ListenableFuture<Boolean> insert(Favourite favourite) {
        final Favourite fav = favourite;
        return service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (doesTitleExist(fav.title).get()) {
                    favouriteDao.updateFavourite(fav);
                    return true; // Already existed and updated it
                } else {
                    favouriteDao.insert(fav);
                    return false; // Did not exist, but it does now
                }
            }
        });
    }

}

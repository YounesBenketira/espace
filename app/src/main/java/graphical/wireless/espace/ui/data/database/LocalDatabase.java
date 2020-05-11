package graphical.wireless.espace.ui.data.database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.ui.FavouritesFragment;
import graphical.wireless.espace.ui.data.EspaceAdapter;

@Database(entities = {Favourite.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract FavouriteDao favouriteDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);

    private static Context sContext;
    private static LocalDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "favourites";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static LocalDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LocalDatabase.class) {
                if (sInstance == null) {
                    sContext = context.getApplicationContext();
                    sInstance = buildDatabase(sContext);
                    sInstance.updateDatabaseCreated(sContext);
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static LocalDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, LocalDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                // Generate the data for pre-population
                                LocalDatabase database = LocalDatabase.getInstance(appContext);

                                // Populate database
                                //List<ProductEntity> products = DataGenerator.generateProducts();
                                //List<CommentEntity> comments = DataGenerator.generateCommentsForProducts(products);

                                // TODO: Create a method to generate dummy data for testing?
                                // List<Favourite> favourites = ;
                                // insertData(database, favourites);

                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated();
                            }
                        });
                    }
                })
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final LocalDatabase database, final List<Favourite> favourites) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.favouriteDao().insertAll(favourites);
            }
        });
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    public static void addData(Favourite favourite, EspaceAdapter adapter) {
        final Favourite[] favourtiesTemp = new Favourite[1];
        favourtiesTemp[0] = favourite;

        new LocalDatabase.FavouriteInsertAsyncTask(sContext, Arrays.asList(favourtiesTemp), adapter).execute();
    }

    public static List<Favourite> getData(EspaceAdapter adapter) {
        LocalDatabase.FavouriteGetAsyncTask favouriteGetAsyncTask = new LocalDatabase.FavouriteGetAsyncTask(sContext, adapter);
        favouriteGetAsyncTask.execute();

        return adapter.getData();
    }

    public static List<Favourite> getData(List<Favourite> list) {
        LocalDatabase.FavouriteGetAsyncTask favouriteGetAsyncTask = new LocalDatabase.FavouriteGetAsyncTask(sContext, list);
        favouriteGetAsyncTask.execute();

        return list;
    }

    public static void delete(Favourite favourite, EspaceAdapter adapter) {
        final Favourite[] favouritesTemp = new Favourite[1];
        favouritesTemp[0] = favourite;

        FavouriteDeleteAsyncTask favouriteGetAsyncTask = new FavouriteDeleteAsyncTask(sContext, Arrays.asList(favouritesTemp), adapter);
        favouriteGetAsyncTask.execute();
    }

    public static class FavouriteGetAsyncTask extends AsyncTask<Void, Void, List<Favourite>> {

        //Prevent leak
        private WeakReference<Context> weakActivity;
        private EspaceAdapter adapter;
        private List<Favourite> favList;

        public FavouriteGetAsyncTask(Context activity, EspaceAdapter adapter) {
            weakActivity = new WeakReference<Context>(activity);
            this.adapter = adapter;
        }

        public FavouriteGetAsyncTask(Context activity, List<Favourite> list) {
            weakActivity = new WeakReference<Context>(activity);
            favList = list;
        }

        @Override
        protected List<Favourite> doInBackground(Void... params) {
            FavouriteDao favouriteDao = LocalDatabase.getInstance(weakActivity.get()).favouriteDao();
            return favouriteDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Favourite> favouriteList) {
            Context activity = weakActivity.get();
            if (activity == null) {
                return;
            }
            if(adapter != null) {
                adapter.updateData(favouriteList);
            } else {
                favList = favouriteList;
            }
        }
    }

    public static class FavouriteInsertAsyncTask extends AsyncTask<Favourite, Void, List<Favourite>> {

        //Prevent leak
        private WeakReference<Context> weakActivity;
        private List<Favourite> favouriteList;
        private EspaceAdapter adapter;

        public FavouriteInsertAsyncTask(Context activity, List<Favourite> favourites, EspaceAdapter adapter) {
            weakActivity = new WeakReference<Context>(activity);
            favouriteList = favourites;
            this.adapter = adapter;
        }

        @Override
        protected List<Favourite> doInBackground(Favourite... favToBeInserted) {
            FavouriteDao favouriteDao = LocalDatabase.getInstance(weakActivity.get()).favouriteDao();

            favouriteDao.insertAll(favouriteList);
            return favouriteDao.getAll();
        }

        @Override
        protected void onPostExecute(List favouriteInserted) {
            Context activity = weakActivity.get();
            if (activity == null) {
                return;
            }
            if(adapter != null) {
                adapter.updateData(favouriteInserted);
            }
        }
    }

    public static class FavouriteDeleteAsyncTask extends AsyncTask<Favourite, Void, List<Favourite>> {

        //Prevent leak
        private WeakReference<Context> weakActivity;
        List<Favourite> favouriteList;
        EspaceAdapter adapter;

        public FavouriteDeleteAsyncTask(Context activity, List<Favourite> favourites, EspaceAdapter adapter) {
            weakActivity = new WeakReference<Context>(activity);
            favouriteList = favourites;
            this.adapter = adapter;
        }

        @Override
        protected List<Favourite> doInBackground(Favourite... favToBeInserted) {
            FavouriteDao favouriteDao = LocalDatabase.getInstance(weakActivity.get()).favouriteDao();
            List<Favourite> temp = favouriteDao.getAll();
            for(int i = 0; i < temp.size(); i++){
                Favourite tempFav = temp.get(i);
                if(tempFav.title.equals(favouriteList.get(0).title))
                    favouriteDao.delete(tempFav);
            }

            return favouriteDao.getAll();
        }

        @Override
        protected void onPostExecute(List favouriteDeleted) {
            Context activity = weakActivity.get();
            if (activity == null) {
                return;
            }
            if(adapter != null) {
                adapter.updateData(favouriteDeleted);
            }
        }
    }
}
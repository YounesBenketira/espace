package graphical.wireless.espace.ui.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Favourite.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract FavouriteDao favouriteDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);

    private static LocalDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "favourites";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static LocalDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LocalDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
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
}
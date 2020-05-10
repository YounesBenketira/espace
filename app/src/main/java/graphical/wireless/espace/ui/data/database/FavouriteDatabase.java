package graphical.wireless.espace.ui.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {CardData.class}, version = 1)
public abstract class FavouriteDatabase extends RoomDatabase {
    public abstract CardDataDao cardDataDao();

    private static FavouriteDatabase.Callback sFavouriteDatabaseCallback = new FavouriteDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // Delete everything from database
                    CardDataDao dao = INSTANCE.cardDataDao();
                    dao.deleteAll();

                    // Can add default database data here

                }
            });
        }
    };

    private static volatile FavouriteDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ListeningExecutorService databaseWriteExecutor = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(NUMBER_OF_THREADS));

    static FavouriteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavouriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavouriteDatabase.class, "CardData")
                            .addCallback(sFavouriteDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
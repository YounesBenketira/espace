package graphical.wireless.espace.ui.data.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardDataDao {
    @Query("SELECT * FROM CardData")
    LiveData<List<CardData>> getAll();

    @Query("SELECT * FROM CardData WHERE uid IN (:userIds)")
    LiveData<List<CardData>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM CardData WHERE title LIKE :title LIMIT 1")
    LiveData<CardData> findByTitle(String title);

    @Query("SELECT * FROM CardData WHERE espace_data == :datatype")
    LiveData<List<CardData>> findByDatatype(int datatype);

    @Query("SELECT * FROM CardData WHERE espace_data == :datatype AND title LIKE :title LIMIT 1")
    LiveData<CardData> findByTitleData(String title, int datatype);

    @Query("SELECT COUNT(*) FROM CardData WHERE title LIKE :title")
    int countFavouritesByTitle(String title);

    @Insert(entity = CardData.class, onConflict = OnConflictStrategy.IGNORE)
    void insert(CardData... favourite);

    @Update(entity = CardData.class)
    void updateFavourite(CardData... favourite);

    @Delete
    void delete(CardData fav);

    @Query("DELETE FROM CardData")
    void deleteAll();
}

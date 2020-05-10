package graphical.wireless.espace.ui.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Query("SELECT * FROM Favourite")
    List<Favourite> getAll();

    @Query("SELECT * FROM Favourite WHERE uid IN (:userIds)")
    List<Favourite> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Favourite WHERE title LIKE :title LIMIT 1")
    Favourite findByTitle(String title);

    @Query("SELECT * FROM Favourite WHERE espace_data == :datatype")
    List<Favourite> findByDatatype(int datatype);

    @Query("SELECT * FROM Favourite WHERE espace_data == :datatype AND title LIKE :title LIMIT 1")
    Favourite findByTitleData(String title, int datatype);

    @Query("SELECT COUNT(*) FROM Favourite WHERE title LIKE :title")
    int countFavouritesByTitle(String title);

    @Insert(entity = Favourite.class)
    void insert(Favourite... favourite);

    @Update(entity = Favourite.class)
    void updateFavourite(Favourite... favourite);

    @Delete
    void delete(Favourite fav);
}

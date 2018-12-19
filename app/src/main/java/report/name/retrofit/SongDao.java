package report.name.retrofit;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SongDao {

    @Query("SELECT * FROM stable")
    List<Song> getAll();

    @Query("SELECT * \n" +
            "    FROM  stable" +
            "    WHERE  uid = (SELECT MAX(uid)  FROM stable);")
    List<Song> getLast();

    @Query("SELECT * FROM stable WHERE uid IN (:userIds)")
    List<Song> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM stable WHERE song_Artist LIKE :first AND " +
            "sont_title LIKE :last LIMIT 1")
    Song findByName(String first, String last);

    @Insert
    void insertSong(Song song);

    @Delete
    void delete(Song user);
}
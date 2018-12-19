package report.name.retrofit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stable")
public class Song {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "sont_title")
    public String songTitle;

    @ColumnInfo(name = "song_Artist")
    public String songArtist;
}
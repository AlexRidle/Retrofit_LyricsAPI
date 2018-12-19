package report.name.retrofit;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class AsyncInsertDB extends AsyncTask<Void, Void, Integer> {

    //Prevent leak
    private WeakReference<Activity> weakActivity;
    private String q_title;
    private String q_artist;
    private SongDatabase db;

    AsyncInsertDB(SongDatabase db) {
        this.db = db;
    }

    public void AgentAsyncTask(Activity activity, String q_artist, String q_title) {
        weakActivity = new WeakReference<>(activity);
        this.q_artist = q_artist;
        this.q_title = q_title;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Song song = new Song();
        song.songTitle = q_title;
        song.songArtist = q_artist;
        db.songDao().insertSong(song);
        return 0;
    }
}

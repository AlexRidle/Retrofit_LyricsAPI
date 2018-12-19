package report.name.retrofit;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class AsyncLoadDB extends AsyncTask<Void, Void, String[]> {

    //Prevent leak
    private WeakReference<Activity> weakActivity;
    private SongDatabase db;

    AsyncLoadDB(SongDatabase db) {
        this.db = db;
    }

    public void AgentAsyncTask(Activity activity) {
        weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String[] request = new String[2];
        request[0] = db.songDao().getLast().get(0).songArtist;
        request[1] = db.songDao().getLast().get(0).songTitle;
        return request;
    }
}

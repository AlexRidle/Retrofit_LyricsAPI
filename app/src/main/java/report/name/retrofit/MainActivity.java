package report.name.retrofit;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<LyricsModel> lyrics;
    ImageView downloadedImage;
    Button submitButton;
    EditText authorText;
    EditText titleText;
    SongDatabase db;
    String q_author = null;
    String q_title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        downloadedImage = findViewById(R.id.imageView);
        submitButton = findViewById(R.id.submitButton);
        authorText = findViewById(R.id.authorSong);
        titleText = findViewById(R.id.titleSong);
        db = Room.databaseBuilder(getApplicationContext(),
                SongDatabase.class, "songdatabase").build();
        try {
            AsyncLoadDB asyncLoadDB = new AsyncLoadDB(db);
            asyncLoadDB.AgentAsyncTask(this);
            String[] lastSong = asyncLoadDB.execute().get();
            authorText.setText(lastSong[0]);
            titleText.setText(lastSong[1]);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadImage();
    }

    public void searchBtnClick(View view) {
        closeKeyboard();
        loadImage();
        q_author = authorText.getText().toString();
        q_title = titleText.getText().toString();
        findLyrics();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void findLyrics() {
        Toast.makeText(MainActivity.this, "Trying to find lyrics.", Toast.LENGTH_SHORT).show();
        LyricsApi lyricsApi = Controller.getApi();
        lyrics = new ArrayList<>();
        recyclerView = findViewById(R.id.posts_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LyricsAdapter adapter = new LyricsAdapter(lyrics);
        recyclerView.setAdapter(adapter);
        lyricsApi.getData("json", q_title, q_author, "47e214a656580b59df089328021315f5").enqueue(new Callback<LyricsModel>() {

            @Override
            public void onResponse(Call<LyricsModel> call, Response<LyricsModel> response) {
                Toast.makeText(MainActivity.this, "Lyrics loaded", Toast.LENGTH_SHORT).show();
                lyrics.add(response.body());
                recyclerView.getAdapter().notifyDataSetChanged();
                AsyncInsertDB asyncInsertDB = new AsyncInsertDB(db);
                asyncInsertDB.AgentAsyncTask(getParent(),q_author,q_title);
                asyncInsertDB.execute();

            }

            @Override
            public void onFailure(Call<LyricsModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImage() {
        Picasso.get()
                .load("https://pp.userapi.com/c852216/v852216405/70e5a/AYyhdpZMKf8.jpg")
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(downloadedImage);
    }
}


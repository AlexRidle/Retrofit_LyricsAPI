package report.name.retrofit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<LyricsModel> lyrics;
    Button submitButton;
    EditText authorText;
    EditText titleText;
    String q_author = null;
    String q_title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = findViewById(R.id.submitButton);
        authorText = findViewById(R.id.authorSong);
        titleText = findViewById(R.id.titleSong);
    }

    public void searchBtnClick(View view){
        q_author=authorText.getText().toString();
        q_title=titleText.getText().toString();
        findLyrics();
    }

    private void findLyrics(){
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
                lyrics.add(response.body());
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LyricsModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
//        lyricsApi.getData("json", "savior", "rise against", "47e214a656580b59df089328021315f5").enqueue(new Callback<List<LyricsModel>>() {
//            public void onResponse(@NonNull Call<List<LyricsModel>> call, @NonNull Response<List<LyricsModel>> response) {
//                lyrics.addAll(response.body());
//            @Override
//                recyclerView.getAdapter().notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<LyricsModel>> call, @NonNull Throwable t) {
//                t.printStackTrace();
//                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}


package report.name.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LyricsApi {
    @GET("matcher.lyrics.get")
//    Call<List<LyricsModel>> getData(@Query("format") String format, @Query("q_track") String q_track, @Query("q_artist") String q_artist, @Query("apikey") String apikey);
    Call<LyricsModel> getData(@Query("format") String format, @Query("q_track") String q_track, @Query("q_artist") String q_artist, @Query("apikey") String apikey);
}


package edu.uci.swe264p.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieListActivity extends AppCompatActivity {
    static final String BASE_URL = "https://api.themoviedb.org/3/";
    static Retrofit retrofit = null;
    final static String API_KEY = "0a787e49870bb26073a5000dc588ba4c";
    static final String TAG = MainActivity.class.getSimpleName();
    List<Movie> movies;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<TopRatedResponse> call = movieApiService.getTopMovie(API_KEY);
        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                movies = response.body().getTopMovies();
            }

            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

        recyclerView = findViewById(R.id.rvMovieList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MovieListAdapter(movies));


    }
}

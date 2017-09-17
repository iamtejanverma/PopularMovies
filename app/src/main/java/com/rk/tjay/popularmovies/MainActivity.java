package com.rk.tjay.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rk.tjay.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.OnItemClickHandler{

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie";

    private static final String API_KEY = "c1b2d49a8cbb244fe64811f9ca9fab6c";

    private static final int LAYOUT_VERTICAL = 1;

    // private static final int LAYOUT_HORIZONTAL = 0;

    private Toast mToast;

    private static final int NUMBERS_OF_COLUMN = 3;

    private RecyclerView mRecyclerView;

    private MovieAdapter mAdapter;

    // private TextView mErrorMessageDisplay;

    // private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);



        GridLayoutManager layoutManager = new
                GridLayoutManager(this, NUMBERS_OF_COLUMN, LAYOUT_VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MovieAdapter(this, this);



        mRecyclerView.setAdapter(mAdapter);

        loadMoviesData();



    }

    private void loadMoviesData(){

        Uri requestUri = Uri.parse(BASE_URL);
        Uri.Builder builder = requestUri.buildUpon();
        builder.appendPath("popular");
        builder.appendQueryParameter("api_key", API_KEY);



        new FetchMoviesTask().execute(builder.toString());

    }

    @Override
    public void onItemClick(Movies currentMovie) {
//        if(mToast != null){
//            mToast.cancel();
//        }
        Context context = this;
//        mToast = Toast.makeText(context, currentMovie.getmTitle(), Toast.LENGTH_LONG);
//        mToast.show();

        Intent startDetailIntent = new Intent(context, DetailActivity.class);
        startDetailIntent.putExtra("moviePoster", currentMovie.getmPoster());
        startDetailIntent.putExtra("movieTitle", currentMovie.getmTitle());
        startDetailIntent.putExtra("movieOverview", currentMovie.getmOverview());
        startDetailIntent.putExtra("movieBackPoster", currentMovie.getmBackPoster());
        startDetailIntent.putExtra("moviePopularity", currentMovie.getmVoteCount());
        startDetailIntent.putExtra("movieRating", currentMovie.getmVoteAverage());
        startDetailIntent.putExtra("movieReleaseDate", currentMovie.getmReleaseDate());

        startActivity(startDetailIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.action_refresh:
                loadMoviesData();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, List<Movies>> {

        @Override
        protected List<Movies> doInBackground(String... urls) {

           if(urls.length == 0 || urls[0] == null) {
               return null;
           }

           List<Movies> result = NetworkUtils.fetchMoviesData(urls[0]);

           return result;
        }

        @Override
        protected void onPostExecute(List<Movies> moviesList) {
           if(!moviesList.isEmpty()){
               mAdapter.setMoviesData(moviesList);
           }
            else {
               Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
           }
        }
    }
}

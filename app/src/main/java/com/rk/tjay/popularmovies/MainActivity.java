package com.rk.tjay.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rk.tjay.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie";

    private static final String API_KEY = "c1b2d49a8cbb244fe64811f9ca9fab6c";

    private static final int LAYOUT_VERTICAL = 1;

    private static final int LAYOUT_HORIZONTAL = 0;

    private static final int NUMBERS_OF_COLUMN = 3;

    private RecyclerView mRecyclerView;

    private MovieAdapter mAdapter;

    private TextView mErrorMessagaDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);



        GridLayoutManager layoutManager = new
                GridLayoutManager(this, NUMBERS_OF_COLUMN, LAYOUT_VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MovieAdapter(this);



        mRecyclerView.setAdapter(mAdapter);

        loadMoviesData();



    }

    public void loadMoviesData(){

        Uri requestUri = Uri.parse(BASE_URL);
        Uri.Builder builder = requestUri.buildUpon();
        builder.appendPath("popular");
        builder.appendQueryParameter("api_key", API_KEY);


        Toast.makeText(getApplicationContext(),builder.toString(), Toast.LENGTH_LONG).show();
        new FetchMoviesTask().execute(builder.toString());

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

package com.rk.tjay.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView mFrontPoster;

    private TextView mOverview;

    private TextView mPopularity;

    private ImageView mBackPoster;

    private TextView mReleaseDate;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    isShow = true;
//                    showOption(R.id.show_rating);
//                } else if (isShow) {
//                    isShow = false;
//                    hideOption(R.id.show_rating);
//                }
//            }
//        });

        Intent recieveIntent = getIntent();

        String imagePath = recieveIntent.getStringExtra("moviePoster");
        String title = recieveIntent.getStringExtra("movieTitle");
        String overview = recieveIntent.getStringExtra("movieOverview");
        String backImage = recieveIntent.getStringExtra("movieBackPoster");
        String voteCount = recieveIntent.getStringExtra("moviePopularity");
        String voteAvg = recieveIntent.getStringExtra("movieRating");
        String releaseDate = recieveIntent.getStringExtra("movieReleaseDate");

        setTitle(title);

        mFrontPoster = (ImageView) findViewById(R.id.detail_movie_poster);
        mOverview = (TextView) findViewById(R.id.detail_overview);
        mBackPoster =(ImageView) findViewById(R.id.expandedImage);
        mPopularity = (TextView) findViewById(R.id.detail_movie_popularity);
        mReleaseDate = (TextView) findViewById(R.id.detail_movie_release_date);

        imagePath = MovieAdapter.loadImageString(imagePath.substring(1));
        backImage = MovieAdapter.loadImageString(backImage.substring(1));

        Picasso.with(this).load(backImage).into(mBackPoster);
        Picasso.with(getApplicationContext()).load(imagePath).into(mFrontPoster);

        mOverview.setText(overview);
        mPopularity.setText(getString(R.string.detail_popularity) + voteCount);
        mReleaseDate.setText(getString(R.string.detail_release_date) + releaseDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_movie, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        } else if (id == R.id.action_info) {
//            return true;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }
}

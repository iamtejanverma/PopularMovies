package com.rk.tjay.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DeviL on 16-09-2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Context mContext;
    private List<Movies> mMoviesList;

    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/";



    public MovieAdapter(Context context) {
        mContext = context;
        //mMoviesList = moviesList;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        view.setFocusable(true);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        Movies movies = mMoviesList.get(position);
        String posterPath = movies.getmPoster();
        Uri baseUri = Uri.parse(IMG_BASE_URL);

        Uri.Builder builder = baseUri.buildUpon();
        builder.appendPath("w185");
        builder.appendPath(posterPath);

        Picasso.with(mContext).load(builder.toString()).into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {

        if(mMoviesList.isEmpty()){
            return 0;
        }
        return mMoviesList.size();

    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        final ImageView moviePoster;

        public MovieAdapterViewHolder(View view) {
            super(view);

            moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
        }
    }

    public void setMoviesData(List<Movies> moviesList){
        mMoviesList = moviesList;
        notifyDataSetChanged();
    }
}

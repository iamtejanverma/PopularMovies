package com.rk.tjay.popularmovies.utilities;

import android.util.Log;

import com.rk.tjay.popularmovies.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeviL on 16-09-2017.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getName();

    public NetworkUtils() {
    }

    public static List<Movies> fetchMoviesData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with HTTP request", e);
        }

        List<Movies> moviesList = extractMovies(jsonResponse);

        return moviesList;

    }


    private static URL createUrl(String requestUrl) {

        URL url = null;

        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem With Creating URL " + requestUrl);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if(url == null){
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();

                jsonResponse = readFromStream(inputStream);

            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem With Reading Stream", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, Charset.forName("UTF-8"));

            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();

            while (line != null){
                output.append(line);
                line = reader.readLine();
            }

        }
        return output.toString();
    }

    private static ArrayList<Movies> extractMovies(String jsonResponse) {

        ArrayList<Movies> moviesArrayList = new ArrayList<>();

        if(jsonResponse == null){
            return null;
        }

        try {
            JSONObject baseJson = new JSONObject(jsonResponse);

            JSONArray moviesArray = baseJson.getJSONArray("results");

            for(int i = 0; i < moviesArray.length(); i++){

                JSONObject currentMovie = moviesArray.getJSONObject(i);

                String moviePoster = currentMovie.getString("poster_path");

                String movieTitle = currentMovie.getString("original_title");

                String movieOverview = currentMovie.getString("overview");

                String movieBackPoster = currentMovie.getString("backdrop_path");

                String movieVoteCount = currentMovie.getString("vote_count");

                String movieVoteAvg = currentMovie.getString("vote_average");

                String movieReleaseDate = currentMovie.getString("release_date");

                Movies movies = new Movies(moviePoster, movieTitle, movieOverview, movieBackPoster,
                         movieVoteCount, movieVoteAvg, movieReleaseDate);

                moviesArrayList.add(movies);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem with parsing json", e);
        }

        return moviesArrayList;
    }


}
package com.rk.tjay.popularmovies;

/**
 * Created by DeviL on 16-09-2017.
 */

public class Movies {

    private String mPoster;

    private String mTitle;

    private String mOverview;

    private String mBackPoster;

    private String mVoteCount;

    private String mVoteAverage;

    private String mReleaseDate;

    public Movies(String mPoster, String mTitle, String mOverview, String mBackPoster, String mVoteCount, String mVoteAverage, String mReleaseDate) {
        this.mPoster = mPoster;
        this.mTitle = mTitle;
        this.mOverview = mOverview;
        this.mBackPoster = mBackPoster;
        this.mVoteCount = mVoteCount;
        this.mVoteAverage = mVoteAverage;
        this.mReleaseDate = mReleaseDate;
    }

    public String getmPoster() {
        return mPoster;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmBackPoster() {
        return mBackPoster;
    }

    public String getmVoteCount() {
        return mVoteCount;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }
}

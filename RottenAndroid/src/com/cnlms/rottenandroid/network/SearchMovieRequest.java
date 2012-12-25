package com.cnlms.rottenandroid.network;

import android.content.Context;
import com.cnlms.rottenandroid.model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/20/12 3:18 AM
 */
public final class SearchMovieRequest extends BaseAsyncTask {

    private List<Movie> searchResults;

    public SearchMovieRequest(Context context, String keyword, RequestListener listener) {

        super(context, listener);

        addQueryParameter("q", keyword);

    }

    @Override
    protected String getRequestEndpointUrl() {
        return Endpoints.URL_MOVIE_SEARCH;
    }


    @Override
    protected void processReceivedData(JSONObject json) throws Exception {

        JSONArray jsonMovies = json.getJSONArray("movies");

        if (jsonMovies.length() != 0) {

            searchResults = new ArrayList<Movie>();

            for (int k = 0; k< jsonMovies.length(); k++) {

                JSONObject jsonCurrentMovie = (JSONObject) jsonMovies.get(k);

                /**
                 *  Create new movie
                 */
                Movie movie = new Movie();

                /**
                 *  Movie Basic Info
                 */
                movie.setTitle(jsonCurrentMovie.getString("title"));
                movie.setYear(jsonCurrentMovie.optString("year", ""));

                /**
                 *  Rating
                 */
                JSONObject jsonRating = jsonCurrentMovie.optJSONObject("ratings");

                if (jsonRating != null)
                    movie.setRating(jsonRating.getInt("critics_score"));

                /**
                 *  Image
                 */
                JSONObject jsonPosters = jsonCurrentMovie.optJSONObject("posters");

                if (jsonPosters != null)
                    movie.setThumbnailImageUrl(jsonPosters.getString("thumbnail"));

                searchResults.add(movie);

            }

        }
    }

    public List<Movie> getSearchResults() {
        return searchResults;
    }
}

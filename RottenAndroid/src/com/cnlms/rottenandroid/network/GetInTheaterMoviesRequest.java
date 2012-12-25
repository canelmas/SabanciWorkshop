package com.cnlms.rottenandroid.network;

import android.content.Context;
import com.cnlms.rottenandroid.model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/19/12 3:04 PM
 */
public final class GetInTheaterMoviesRequest extends BaseAsyncTask {

    private List<Movie> movies;

    public GetInTheaterMoviesRequest(Context context, RequestListener listener) {
        super(context, listener);
    }

    @Override
    protected String getRequestEndpointUrl() {
        return Endpoints.URL_IN_THEATER_MOVIES;
    }

    @Override
    protected void processReceivedData(JSONObject json) throws Exception {

        JSONArray jsonMovies = json.getJSONArray("movies");

        if (jsonMovies.length() != 0) {

            movies = new ArrayList<Movie>();

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
                movie.setSynopsis(jsonCurrentMovie.getString("synopsis"));

                /**
                 *  Rating
                 */
                JSONObject jsonRating = jsonCurrentMovie.getJSONObject("ratings");

                movie.setRating(jsonRating.getInt("critics_score"));

                /**
                 *  Image
                 */
                JSONObject jsonPosters = jsonCurrentMovie.getJSONObject("posters");

                movie.setThumbnailImageUrl(jsonPosters.getString("thumbnail"));

                /**
                 *  IMDB id
                 */
                JSONObject jsonAlternateIds = jsonCurrentMovie.optJSONObject("alternate_ids");

                if (jsonAlternateIds != null)
                    movie.setImdbId(jsonAlternateIds.getString("imdb"));

                movies.add(movie);

            }

        }

    }

    public List<Movie> getMovies() {
        return movies;
    }
}

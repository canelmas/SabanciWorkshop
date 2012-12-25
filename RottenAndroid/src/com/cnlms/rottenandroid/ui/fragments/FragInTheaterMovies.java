package com.cnlms.rottenandroid.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.cnlms.rottenandroid.R;
import com.cnlms.rottenandroid.model.Movie;
import com.cnlms.rottenandroid.network.BaseAsyncTask;
import com.cnlms.rottenandroid.network.GetInTheaterMoviesRequest;
import com.cnlms.rottenandroid.ui.activities.ActMovieDetail;
import com.cnlms.rottenandroid.ui.adapters.MovieAdapter;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/19/12 2:31 PM
 */
public final class FragInTheaterMovies extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listviewMovies;
    private MovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_movies, container, false);

        /**
         *  initialize list view
         *
         *  set listView adapter
         *
         *  listen for listView item clicks
         */
        listviewMovies = (ListView) view.findViewById(R.id.listView_movies);

        adapter = new MovieAdapter(getActivity());

        listviewMovies.setAdapter(adapter);

        listviewMovies.setOnItemClickListener(this);

        /**
         *  Test Request
         */
        /*TestRequest testRequest = new TestRequest(getActivity(), new BaseAsyncTask.RequestListener() {

            @Override
            public void onSuccess(BaseAsyncTask request) {

                Toast.makeText(getActivity(), "Fetched Data = " + ((TestRequest) request).getData(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(BaseAsyncTask request) {
                Toast.makeText(getActivity(), "Error!\n" +request.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        testRequest.execute();*/


        /**
         *  Make new Request
         */
        GetInTheaterMoviesRequest request = new GetInTheaterMoviesRequest(getActivity(), new BaseAsyncTask.RequestListener() {

            @Override
            public void onSuccess(BaseAsyncTask request) {

                adapter.setMovies(((GetInTheaterMoviesRequest) request).getMovies());

            }

            @Override
            public void onFailure(BaseAsyncTask request) {

                Toast.makeText(getActivity(), "Error!\n" +request.getException().getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        request.execute();

        return view;

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Movie selectedMovie = (Movie) parent.getAdapter().getItem(position);

        /**
         *  Go to Movie Detail Activity when a movie is selected
         */
        Intent intent = new Intent(getActivity(), ActMovieDetail.class);

        /**
         *  Pass required parameters as bundle
         */
        Bundle bundle = new Bundle();

        bundle.putString("movie_title",         selectedMovie.getTitle());
        bundle.putString("movie_synopsis",      selectedMovie.getSynopsis());
        bundle.putString("movie_imdb_id",       selectedMovie.getImdbId());

        intent.putExtras(bundle);

        /**
         *  Start new activity
         */
        startActivity(intent);

    }
}

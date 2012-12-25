package com.cnlms.rottenandroid.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cnlms.rottenandroid.R;
import com.cnlms.rottenandroid.model.Movie;
import com.cnlms.rottenandroid.network.BaseAsyncTask;
import com.cnlms.rottenandroid.network.SearchMovieRequest;
import com.cnlms.rottenandroid.ui.activities.ActMovieDetail;
import com.cnlms.rottenandroid.ui.adapters.SearchResultAdapter;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/19/12 2:32 PM
 */
public final class FragSearch extends Fragment implements AdapterView.OnItemClickListener {

    private EditText editTextKeyword;
    private Button btnSearch;

    private ListView listViewSearchResult;
    private SearchResultAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_search, container, false);

        /**
         *  Keyword Edit TextField
         */
        editTextKeyword = (EditText) view.findViewById(R.id.editText_search);

        /**
         *  Search Button that will trigger search request
         */
        btnSearch = (Button) view.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                search(editTextKeyword.getText().toString());
            }

        });

        /**
         *  Search Results List View
         */
        listViewSearchResult = (ListView) view.findViewById(R.id.listView_movies);

        listViewSearchResult.setOnItemClickListener(this);

        adapter = new SearchResultAdapter(getActivity());

        listViewSearchResult.setAdapter(adapter);


        return view;
    }

    private void search(String keyword) {

        SearchMovieRequest request = new SearchMovieRequest(getActivity(), keyword, new BaseAsyncTask.RequestListener() {

            @Override
            public void onSuccess(BaseAsyncTask request) {

                adapter.setMovies( ((SearchMovieRequest)request).getSearchResults() );

            }

            @Override
            public void onFailure(BaseAsyncTask request) {

                Toast.makeText(getActivity(), "Error!\n" +request.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        request.execute();
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

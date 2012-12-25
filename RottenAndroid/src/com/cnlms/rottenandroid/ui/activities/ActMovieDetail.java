package com.cnlms.rottenandroid.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.cnlms.rottenandroid.R;
import com.cnlms.rottenandroid.network.Endpoints;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/20/12 1:38 AM
 */
public final class ActMovieDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_movie_detail);

        /**
         *  Read passed data
         */
        Bundle bundle = getIntent().getExtras();

        String title        = bundle.getString("movie_title");
        String synopsis     = bundle.getString("movie_synopsis");
        final String imdbId = bundle.getString("movie_imdb_id");

        /**
         *  Update Action bar title
         */
        getActionBar().setTitle(title);

        /**
         *  Set content
         */
        TextView txtViewMovieTitle = (TextView) findViewById(R.id.movie_title);
        txtViewMovieTitle .setText(title);

        TextView txtViewSynopsis = (TextView) findViewById(R.id.movie_synopsis);
        txtViewSynopsis.setText(synopsis);

        Button btnGoToIMDB = (Button) findViewById(R.id.btn_go_to_imdb);
        btnGoToIMDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String imdbURL = Endpoints.URL_IMDB + imdbId;

                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdbURL));

                startActivity(webIntent);

            }

        });

    }
}

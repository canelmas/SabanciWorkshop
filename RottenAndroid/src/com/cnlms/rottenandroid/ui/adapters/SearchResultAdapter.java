package com.cnlms.rottenandroid.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cnlms.rottenandroid.R;
import com.cnlms.rottenandroid.model.Movie;

import java.util.List;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/20/12 3:56 AM
 */
public final class SearchResultAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<Movie> movies;

    public SearchResultAdapter(Context context) {

        inflater = LayoutInflater.from(context);

    }

    public void setMovies(List<Movie> movies) {

        this.movies = movies;

        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return movies != null ? movies.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = movies.get(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            //  not recycled

            convertView = inflater.inflate(R.layout.item_search_result, parent, false);

            /**
             *  View Holder Pattern
             */
            viewHolder = new ViewHolder();

            viewHolder.movieImage       = (ImageView) convertView.findViewById(R.id.movie_img);
            viewHolder.movieTitle       = (TextView) convertView.findViewById(R.id.movie_title);
            viewHolder.movieYear        = (TextView) convertView.findViewById(R.id.movie_year);

            /**
             *  set holder as view's tag
             */
            convertView.setTag(viewHolder);

        } else {

            //  recycled view
            viewHolder = (ViewHolder) convertView.getTag();

        }

        /**
         *  Set View Holder's Content
         */
        viewHolder.movieTitle.setText(movie.getTitle());
        viewHolder.movieYear.setText(movie.getYear());
//        viewHolder.movieImage.setImageBitmap(Drawable.);


        return convertView;
    }

    static class ViewHolder {

        ImageView movieImage;
        TextView movieTitle;
        TextView movieYear;

    }

}

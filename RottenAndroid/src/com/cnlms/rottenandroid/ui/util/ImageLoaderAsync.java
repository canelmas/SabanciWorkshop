package com.cnlms.rottenandroid.ui.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;

/**
 * Author: Emmar Kardeslik <emmar.kardeslik@pozitron.com>
 * Created: 05.04.2012
 */
public class ImageLoaderAsync {

    private static final String LOG_TAG = "RottenAndroid";

    private static final ImageLoaderAsync INSTANCE = new ImageLoaderAsync();

    public static ImageLoaderAsync getInstance() {
        return INSTANCE;
    }

    private final ConcurrentHashMap<ImageView, AsyncTask<Void, Void, Bitmap>> tasks = new ConcurrentHashMap<ImageView, AsyncTask<Void, Void, Bitmap>>();

    private final ConcurrentHashMap<String, SoftReference<Bitmap>> bitmaps = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

    public void clearMemoryCache() {

//        bitmaps.clear();

    }

    private Bitmap fetchBitmap(final String url, final AsyncTask<Void, Void, Bitmap> task) {

        try {

            if (url.length() == 0) {
                return null;
            }

            /**
             *  uncomment
             */
            SoftReference<Bitmap> ref = bitmaps.get(url);

            if (ref != null) {

                Bitmap bitmap = ref.get();

                if (bitmap != null) {

                    Log.v(LOG_TAG, "BITMAP CACHE HIT: " + url);

                    return bitmap;

                } else {

                    bitmaps.remove(url);

                }

            }

            Log.v(LOG_TAG, "BITMAP CACHE MISS: " + url);

            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpGet request = new HttpGet(url);

            HttpResponse response = httpClient.execute(request);

            // maybe cancelled before we read the body
            if (task.isCancelled()) {
                return null;
            }

            byte[] buf = new byte[(int) response.getEntity().getContentLength()];

            // maybe cancelled before we start reading
            if (task.isCancelled()) {
                return null;
            }

            InputStream is = response.getEntity().getContent();

            // maybe cancelled before we start reading
            if (task.isCancelled()) {
                return null;
            }

            int read = is.read(buf, 0, buf.length);

            while (read < buf.length) {

                // maybe cancelled when we are still reading
                if (task.isCancelled()) {
                    return null;
                }

                read += is.read(buf, read, buf.length - read);

            }

            // maybe cancelled before we create the image
            if (task.isCancelled()) {
                return null;
            }

            Bitmap bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);

            if (task.isCancelled()) {
                return null;
            }

            /**
             *  todo :
             */
            bitmaps.put(url, new SoftReference<Bitmap>(bitmap));

            return bitmap;

        } catch (Exception e) {

            Log.w("", Log.getStackTraceString(e));

            return null;

        }

    }

    public void fetchBitmapAsync(final String url, final ImageView imageView) {

        AsyncTask<Void, Void, Bitmap> task;

        if (imageView != null) {

            task = tasks.get(imageView);

            // cancel if there is an existing task for this image view
            if (task != null) {
                task.cancel(true);
            }

        }

        task = new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //if (imageView != null) {
                //imageView.setImageResource(R.drawable.list_noimage);
                //}
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {

                super.onPostExecute(bitmap);

                if (bitmap != null && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }

            }

            @Override
            protected Bitmap doInBackground(Void... aVoid) {
                return fetchBitmap(url, this);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Log.v("HepsiBurada", "FETCH CANCELLED for " + url);
            }

        };

        if (imageView != null) {
            tasks.put(imageView, task);
        }

        try {

            task.execute();

        } catch (RejectedExecutionException e) {

            Log.w("HepsiBurada", Log.getStackTraceString(e));

        }

    }


}
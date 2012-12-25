package com.cnlms.rottenandroid.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/19/12 12:09 PM
 */
public abstract class BaseAsyncTask extends AsyncTask<Void, Void, Void>{

    /**
     *  Logger tag
     */
    private static final String LOG_TAG = "RottenAndroid";

    /**
     *  Rotten Tomatoes API Key
     */
    private static final String API_KEY = "pmdqn6x39spbuxht7h5x2aye";

    /**
     *  Current context
     */
    protected Context context;

    /**
     *  Exception
     */
    protected Exception exception;

    /**
     *  Loading Progress Dialog
     */
    private ProgressDialog progressDialog;

    /**
     *  Request Listener
     */
    private RequestListener listener;

    /**
     *  Result Pagination Page
     */
    protected int currentPage = 1;

    /**
     *  Get Parameters
     */
    private Map<String, String> parameters = new HashMap<String, String>();

    /**
     *  Request Response Listener
     */
    public interface RequestListener {

        void onSuccess(BaseAsyncTask request);

        void onFailure(BaseAsyncTask request);

    }

    public BaseAsyncTask(Context context, RequestListener listener) {

        this.context    = context;
        this.listener   = listener;
    }

    @Override
    protected void onPreExecute() {

        /**
         *  Show progress dialog
         */
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");

        progressDialog.show();

    }

    @Override
    protected Void doInBackground(Void... params) {

        try {

            /**
             *  Fetch data over network
             */
            JSONObject json = fetchData();

            /**
             *  Process your action i.e fetch, parse, process..
             */
            processReceivedData(json);

        } catch (Exception e) {

            Log.e(LOG_TAG, "Exception Occurred with the async task! ", e);

            this.exception = e;
        }

        return null;

    }

    /**
     *  Appends Get parameters to the end of the url
     */
    public void addQueryParameter(String paramKey, String paramValue) {

        parameters.put(paramKey, paramValue);

    }

    private String generateUrl() throws UnsupportedEncodingException {

        StringBuilder sb = new StringBuilder();

        sb.append(getRequestEndpointUrl());
        sb.append("?apikey=" + API_KEY);
        sb.append("&page_limit=30");
        sb.append("&page=");
        sb.append(currentPage);

        if (!parameters.isEmpty()) {

            for (String paramKey : parameters.keySet()) {

                sb.append("&" + paramKey + "=" + URLEncoder.encode(parameters.get(paramKey), "utf-8"));
            }

        }

        return sb.toString();
    }

    private JSONObject fetchData() throws Exception {

        JSONObject jsonObject;

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            /**
             *  Apache
             */

            String url = generateUrl();

            Log.e(LOG_TAG, "request=" + url);

            HttpGet request = new HttpGet(url);

            request.setHeader("Accept",     "application/json");
            request.setHeader("Accept",     "gzip, deflate");


            HttpClient client = new DefaultHttpClient();

            HttpResponse httpResponse = client.execute(request);

            HttpEntity entity = httpResponse.getEntity();

            is = entity.getContent();

            int status = httpResponse.getStatusLine().getStatusCode();

            if (status == 200) {

                String jsonAsString = new String(streamToBytes(is));

                Log.e(LOG_TAG, "response=" + jsonAsString);

                jsonObject = (JSONObject)new JSONTokener(jsonAsString).nextValue();

            } else throw new Exception("HTTP Status Not 200!");

        } catch (Exception e) {

            Log.e(LOG_TAG, "Fetching Data Failed! ");

            throw e;

        } finally {

            try {

                if (is != null) is.close();

            } catch (Exception e) {

                Log.w(LOG_TAG, e.getMessage(), e);
            }

            try {

                conn.disconnect();

            } catch (Exception e) {

                Log.w(LOG_TAG, e.getMessage(), e);
            }
        }

        return jsonObject;

    }

    private static byte[] streamToBytes(InputStream is) {

        byte byteData[] = new byte[1024];
        byte returnBytes[] = null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        try {

            int c;
            int totalBytes = 0;

            // Read the data a chunk at a time accumulating
            // the bytes in the stream
            while ((c = is.read(byteData, 0, byteData.length)) != -1)  {
                bytes.write(byteData, 0, c);
                totalBytes += c;
            }

            // If we read anything, convert the stream to a
            // byte array
            if (totalBytes > 0)
                returnBytes = bytes.toByteArray();

            bytes.close();

        }
        catch (IOException e) {}

        return returnBytes;

    }


    @Override
    protected void onPostExecute(Void aVoid) {

        /**
         *  Dismiss loading dialog
         */
        if (progressDialog != null) progressDialog.dismiss();


        /**
         *  We're assuming that the request has succeeded if
         *  we haven't catch any exception
         */
        if (exception == null) {

            listener.onSuccess(this);

        } else {

            listener.onFailure(this);

        }

    }

    /**
     *  Returns action endpoint URL
     */
    protected abstract String getRequestEndpointUrl();

    /**
     *  Action Specific Proces Method to be implemented
     */
    protected abstract void processReceivedData(JSONObject json) throws Exception;

    public Exception getException() {
        return exception;
    }
}

package com.cnlms.rottenandroid.network;

import android.content.Context;
import org.json.JSONObject;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/20/12 5:31 AM
 */
public class TestRequest extends BaseAsyncTask {

    private String data;

    public TestRequest(Context context, RequestListener listener) {
        super(context, listener);
    }

    @Override
    protected String getRequestEndpointUrl() {
        return Endpoints.URL_MOVIE_SEARCH;
    }

    @Override
    protected void processReceivedData(JSONObject json) throws Exception {

        /**
         *  Parse json object and initialize model classes fetched from the endpoint
         */
        data = json.getString("data");

    }

    public String getData() {
        return data;
    }
}

package com.sabanci.demo;

import android.app.Activity;
import android.os.Bundle;


public class ActivityTwo extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /**
         *  second activity ui content
         */
        setContentView(R.layout.second);

    }
}

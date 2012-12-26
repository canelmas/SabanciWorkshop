package com.sabanci.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityOne extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /**
         *  Set UI Content
         */
        setContentView(R.layout.main);

        /**
         *  Navigate user to the second activity when button is pressed
         */
        Button button = (Button) findViewById(R.id.myButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityOne.this, ActivityTwo.class);

                startActivity(intent);
            }
        });
    }
}

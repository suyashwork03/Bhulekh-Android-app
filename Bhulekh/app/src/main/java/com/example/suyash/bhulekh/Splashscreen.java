package com.example.suyash.bhulekh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splashscreen extends Activity {

    private Thread splashthread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        splashthread = new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                intent.setClass(Splashscreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        splashthread.start();

    }
}

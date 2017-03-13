package com.lipy.annulusprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by lipy on 2017/3/11 0009.
 */
public class MainActivity extends AppCompatActivity {

    private AnnulusProgress mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (AnnulusProgress) findViewById(R.id.annulus);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_start) {
            mProgressBar.setProgress(0);
            mProgressBar.reSet();
            final int count = 5;
            new Thread(new Runnable() {

                @Override
                public void run() {

                    while (mProgressBar.getProgress() < 100) {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mProgressBar.getProgress() < 100) {
                                        mProgressBar.setProgress(mProgressBar.getProgress() + count);
                                    }
                                }
                            });
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setResult(true);
                        }
                    });
                }
            }).start();
        }
        return true;
    }
}

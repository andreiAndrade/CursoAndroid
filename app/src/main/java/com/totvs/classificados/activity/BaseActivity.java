package com.totvs.classificados.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Totvs on 20/12/2016.
 */

public class BaseActivity extends Activity {

    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate " + TAG);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart " + TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause " + TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop " + TAG);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart " + TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume " + TAG);
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish " + TAG);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed " + TAG);
    }
}

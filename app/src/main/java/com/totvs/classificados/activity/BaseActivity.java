package com.totvs.classificados.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.totvs.classificados.R;

/**
 * Created by Totvs on 20/12/2016.
 */

public class BaseActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();

    protected Toolbar toolbar;

//    protected AppCompatSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate " + TAG);
    }

    protected void createToolbar(String title) {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar.setTitle(title);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();

        return super.onOptionsItemSelected(item);
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

    public SharedPreferences getPrefs() {
        return getSharedPreferences("com.totvs.classificados", MODE_PRIVATE);
    }
}

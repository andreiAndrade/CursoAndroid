package com.totvs.classificados;

import android.app.Activity;
import android.app.Application;

import com.totvs.classificados.database.DBHelper;

/**
 * Created by Totvs on 27/12/2016.
 */

public class App extends Application {
    private Long mCurrentTime;
    private DBHelper mDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        mDbHelper = new DBHelper(getApplicationContext());
    }

    public synchronized DBHelper getDbHelper() {
        return mDbHelper;
    }

    public static App getApp(Activity activity) {
        return (App) activity.getApplication();
    }
    public Long getCurrentTime() {
        return mCurrentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.mCurrentTime = currentTime;
    }
}

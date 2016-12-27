package com.totvs.classificados;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Totvs on 27/12/2016.
 */

public class App extends Application {
    private Long mCurrentTime;

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

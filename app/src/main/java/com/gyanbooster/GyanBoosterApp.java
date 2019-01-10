package com.gyanbooster;


import android.support.multidex.MultiDexApplication;

import com.gyanbooster.shared_preference.GyanBoosterPreferences;

/**
 * Created by Dell on 1/31/2018.
 */
public class GyanBoosterApp extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        GyanBoosterPreferences.init(this);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
                System.exit(1);
            }
        });
    }


}

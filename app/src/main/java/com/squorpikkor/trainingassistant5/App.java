package com.squorpikkor.trainingassistant5;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class App extends Application {

    public static final String TAG = "Tag";
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
//        showLogo();
    }

    public static Context getContext(){
        return mApplication.getApplicationContext();
    }

    static void showLogo() {
        Log.e(TAG, "╔═══╗╔═══╗╔═══╗╔═╗ ╔╗   ╔═══╗╔═══╗╔═══╗╔═╗ ╔╗╔═╗ ╔╗╔═══╗╔═══╗");
        Log.e(TAG, "║╔══╝║╔═╗║║╔═╗║║ ╚╗║║   ║╔═╗║║╔═╗║║╔═╗║║ ╚╗║║║ ╚╗║║║╔══╝║╔═╗║");
        Log.e(TAG, "║║╔═╗║╚═╝║║║ ║║║╔╗╚╝║   ║╚══╗║║ ╚╝║║ ║║║╔╗╚╝║║╔╗╚╝║║╚══╗║╚═╝║");
        Log.e(TAG, "║║╚╗║║╔╗╔╝║╚═╝║║║╚╗ ║   ╚══╗║║║ ╔╗║╚═╝║║║╚╗ ║║║╚╗ ║║╔══╝║╔╗╔╝");
        Log.e(TAG, "║╚═╝║║║║╚╗║╔═╗║║║ ║ ║   ║╚═╝║║╚═╝║║╔═╗║║║ ║ ║║║ ║ ║║╚══╗║║║╚╗");
        Log.e(TAG, "╚═══╝╚╝╚═╝╚╝ ╚╝╚╝ ╚═╝   ╚═══╝╚═══╝╚╝ ╚╝╚╝ ╚═╝╚╝ ╚═╝╚═══╝╚╝╚═╝");
        Log.e(TAG, "♣VERSION_NAME: " + BuildConfig.VERSION_NAME);
    }
}

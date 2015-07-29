package com.kilobolt;

import android.app.Application;
import android.os.Handler;
import android.provider.Settings;

import com.kilobolt.framework.threading.RepeatingTask;

/**
 * Created by Ioana.Radoi on 6/12/2015.
 */
public class RobotGameApp extends Application {
    private static RobotGameApp instance;
    public static RobotGameApp instance() {
        return instance;
    }

    public String android_id;

    public RobotGameApp(){
        RobotGameApp.instance = this;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println("RobotGameApp.onCreate " + android_id);


    }
}

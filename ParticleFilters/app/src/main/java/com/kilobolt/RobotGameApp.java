package com.kilobolt;

import android.app.Application;
import android.os.Handler;

import com.kilobolt.framework.threading.RepeatingTask;

/**
 * Created by Ioana.Radoi on 6/12/2015.
 */
public class RobotGameApp extends Application {
    private static RobotGameApp instance;
    public static RobotGameApp instance() {
        return instance;
    }

    public RobotGameApp(){
        RobotGameApp.instance = this;
    }

    @Override
    public void onCreate(){
        super.onCreate();

    }
}

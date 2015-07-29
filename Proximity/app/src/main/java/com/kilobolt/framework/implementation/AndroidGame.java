package com.kilobolt.framework.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.kilobolt.framework.Audio;
import com.kilobolt.framework.FileIO;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.threading.Accumulator;
import com.kilobolt.framework.threading.RepeatingTask;
import com.kilobolt.robotgame.GameScreen;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algo.*;
import sensors.BeaconManager2;
import auxiliar.Constants;

public abstract class AndroidGame extends Activity implements Game, BeaconConsumer {
    AndroidFastRenderView renderView;
    ParticlePaint pp;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    BeaconManager2 beaconManager;
    RangeNotifier rangeNotifier = new RangeNotifier() {
        @Override
        public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            for (Beacon beacon : beacons) {

                accumulator.add(beacon.getId2().toInt() + "/" + beacon.getId3().toInt(), beacon.getRssi());
            }
        }
    };
    Accumulator accumulator = new Accumulator();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (GameScreen.getRobot() == null) {
                return;
            }
            Map<String, List<Integer>> map = accumulator.get();

            //---------------------------------fingerprinting


            String[] id_beacons = new String[auxiliar.Constants.NUMAR_BEACONURI];


//proximitate

            id_beacons[0] = "212/64069";
            id_beacons[1] = "212/64093";
            id_beacons[2] = "213/1321";
            id_beacons[3] = "212/64621";
            id_beacons[4] = "212/64087";
            id_beacons[5] = "212/64598";
            id_beacons[6] = "213/1311";
            id_beacons[7] = "213/1316";
            id_beacons[8] = "213/1323";
            id_beacons[9] = "22784/59692";
            id_beacons[10] = "55367/4626";
            id_beacons[11] = "62637/27728";


            Map<String, Integer> mapAllBeacons = new HashMap<String, Integer>();
            for (int k = 0; k < Constants.NUMAR_BEACONURI; k++) {

                List<Integer> list2 = null;
                if (map.get(id_beacons[k]) != null && map.get(id_beacons[k]).size() != 0) {
                    list2 = map.get(id_beacons[k]);

                    Integer popular = findPopular(list2);

                    System.out.println("Pentru beacon-ul " + id_beacons[k] + " am valoarea " + popular);


                    mapAllBeacons.put(id_beacons[k], popular);
                }
            }


            GameScreen.getProximitate().setValReceivedProximitate(mapAllBeacons);


            System.out.print("A trecut pe aici :) !");

        }
    };
    RepeatingTask task = new RepeatingTask(runnable, 1000);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 600 : 800;
        int frameBufferHeight = isPortrait ? 800 : 600;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);

        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);

        pp = new ParticlePaint(this);


        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();
        setContentView(renderView);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");


        beaconManager = new BeaconManager2(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
        pp.resume();
        task.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        pp.pause();
        screen.pause();
        task.stop();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {

        return screen;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.startListening(rangeNotifier);
    }


    public double getDistance(double rssi, int txPower, float n) {
        return Math.pow(10, ((double) txPower - rssi) / (10 * n));


    }

    public int findPopular(List<Integer> a) {
        if (a == null || a.size() == 0)
            return 0;

        Collections.sort(a);

        int previous = a.get(0);
        int popular = previous;
        int count = 1;
        int maxCount = 1;

        for (int i = 1; i < a.size(); i++) {
            if (a.get(i) == previous)
                count++;
            else {
                if (count > maxCount) {
                    popular = a.get(i - 1);
                    maxCount = count;
                }
                previous = a.get(i);
                count = 1;
            }
        }

        return count > maxCount ? a.get(a.size() - 1) : popular;

    }
}
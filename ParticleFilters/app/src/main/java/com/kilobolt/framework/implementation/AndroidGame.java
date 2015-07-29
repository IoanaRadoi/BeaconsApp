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
import java.util.List;
import java.util.Map;

import algo.*;
import auxiliar.Constants;
import sensors.BeaconManager2;

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
            Map<String, List<Double>> map = accumulator.get();

            String[] id_beacons = new String[Constants.NUMAR_BEACONURI];

            id_beacons[0] = "212/64069";
            id_beacons[1] = "212/64093";
            id_beacons[2] = "213/1321";
            id_beacons[3] = "212/64621";
            id_beacons[4] = "212/64087";

            double[] distances = new double[Constants.NUMAR_BEACONURI];
            for (int j = 0; j < Constants.NUMAR_BEACONURI; j++) {
                distances[j] = 0;
            }

            for (int k = 0; k < Constants.NUMAR_BEACONURI; k++) {
                List<Double> list2 = null;
                if (map.get(id_beacons[k]) != null && map.get(id_beacons[k]).size() != 0) {
                    list2 = map.get(id_beacons[k]);

                    int count2 = 0;
                    int sum2 = 0;

                    for (Double l : list2) {

                        sum2 += l;
                        count2++;
                    }
                    double medieRssi = sum2 / count2;
                    distances[k] = getDistance(medieRssi, -70, 2) * 100;
                    System.out.println("Id beacon " + id_beacons[k] + " dist " + k + " este: " + distances[k]);
                }
            }

            GameScreen.getRobot().setDistances_beacons(distances);

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

    public static double getDistance(double rssi, int txPower, float n) {
        return Math.pow(10, ((double) txPower - rssi) / (10 * n));

    }
}
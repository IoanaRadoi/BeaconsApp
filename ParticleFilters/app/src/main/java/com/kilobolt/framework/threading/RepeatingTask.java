package com.kilobolt.framework.threading;

import android.os.AsyncTask;
import android.os.Handler;

/**
 * Created by Ioana.Radoi on 6/12/2015.
 */
public class RepeatingTask {
    private final long interval;
    private final Runnable runnable;
    private volatile boolean running = false;
    private final RepeaterRunnable repeater = new RepeaterRunnable();
    private volatile Handler handler = null;

    public RepeatingTask(Runnable runnable, long interval){
        this.interval = interval;
        this.runnable = runnable;
    }

    /**
     * O sa fie apelat o data la @interval milisecunde pe un background thread
     */
    public void start(){
        running = true;
        handler = new Handler();
        handler.post(repeater);
    }

    public void stop(){
        running = false;
        handler.removeCallbacks(repeater);
    }

    class BackgroundTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            runnable.run();
            return null;
        }
    }

    class RepeaterRunnable implements Runnable{
        @Override
        public void run() {
            if(running) {
                (new BackgroundTask()).execute();
                handler.postDelayed(this, interval);
            }
        }
    }
}

package com.kilobolt.framework.implementation;

import com.kilobolt.framework.threading.RepeatingTask;

/**
 * Created by Ioana.Radoi on 6/24/2015.
 */
public class ProcessSend implements Runnable {

    AndroidGame game;
    RepeatingTask repeatTask =null;

    public ProcessSend(AndroidGame game) {
        this.game = game;
    }

    public void run() {
        game.getCurrentScreen().sendData();
    }

    public void resume() {
        repeatTask = new RepeatingTask(this, 20000);
        repeatTask.start();
    }

    public void pause() {
        repeatTask.stop();


    }
}

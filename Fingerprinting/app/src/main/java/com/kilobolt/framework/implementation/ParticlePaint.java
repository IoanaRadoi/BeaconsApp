package com.kilobolt.framework.implementation;

import com.kilobolt.framework.threading.RepeatingTask;

/**
 * Created by Ioana.Radoi on 6/12/2015.
 */
public class ParticlePaint implements Runnable {

    AndroidGame game;
    RepeatingTask repeatTask =null;

    public ParticlePaint(AndroidGame game) {
        this.game = game;
    }

    public void run() {
        game.getCurrentScreen().updateParticle();
    }

    public void resume() {
        repeatTask = new RepeatingTask(this, 2000);
        repeatTask.start();
    }

    public void pause() {
        repeatTask.stop();


    }
}


package com.stashinvest.stashchallenge.util.rx;

import io.reactivex.Scheduler;

public interface RxSchedulers {

    Scheduler runOnBackground();

    Scheduler io();

    Scheduler compute();

    Scheduler androidThread();

    Scheduler internet();

}

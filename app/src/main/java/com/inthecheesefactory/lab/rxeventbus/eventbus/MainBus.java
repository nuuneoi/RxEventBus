package com.inthecheesefactory.lab.rxeventbus.eventbus;

import com.inthecheesefactory.rxeventbus.RxEventBus;

/**
 * Created by nuuneoi on 1/19/2016.
 */
public class MainBus extends RxEventBus {

    private static MainBus instance;

    public static MainBus getInstance() {
        if (instance == null)
            instance = new MainBus();
        return instance;
    }

    private MainBus() {
    }

}

package com.inthecheesefactory.lab.rxeventbus.eventbus.event;

import android.view.View;

/**
 * Created by nuuneoi on 1/19/2016.
 */
public class ClickEvent {

    private View view;

    public ClickEvent(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

}

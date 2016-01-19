package com.inthecheesefactory.lab.rxeventbus.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.inthecheesefactory.lab.rxeventbus.R;
import com.inthecheesefactory.lab.rxeventbus.eventbus.MainBus;
import com.inthecheesefactory.lab.rxeventbus.eventbus.event.ClickEvent;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainBus.getInstance().post(new ClickEvent(view));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /********************
     * EventBus Example *
     *********************/

    private Subscriber<? super Object> mainBusSubscriber = new Subscriber<Object>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Object o) {
            if (o instanceof  ClickEvent) {
                ClickEvent event = (ClickEvent) o;
                Snackbar.make(event.getView(), "Event Received", Snackbar.LENGTH_LONG)
                        .setAction("OK", null)
                        .show();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // Subscribe to EventBus
        MainBus.getInstance().getBusObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainBusSubscriber);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unsubscribe from EventBus
        mainBusSubscriber.unsubscribe();
    }


}

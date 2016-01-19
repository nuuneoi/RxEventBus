# RxEventBus
A code example of the implementation of RxAndroid &amp; RxJava as an EventBus on Android

# Explaination

This example depends on RxJava and RxAndroid dependencies.

```sh
    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'io.reactivex:rxjava:1.1.0'
```

This is an implementation of `RxEventBus` class which apply RxJava and RxAndroid to be an EventBus with help from RxJava's `SerializedSubject`

```java
public class RxEventBus {

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public void post(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> getBusObservable() {
        return bus;
    }

}
```

Create a Main Bus as a Singleton.

```java
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
```

Declare a Bus Event.

```java
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
```

# Usage

This part of code show you how to subscribe or unsubscribe to the EventBus.

```java

public class MainActivity extends AppCompatActivity {

    ...

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
```

And this is how we post an Event to the bus.

```java
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainBus.getInstance().post(new ClickEvent(view));
            }
        });
```


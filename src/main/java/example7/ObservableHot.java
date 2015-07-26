package example7;

import base.ExecutableExample;
import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

public class ObservableHot implements ExecutableExample
{
    public void execute() {
        Observable<Integer> op1 = Observable.create(observer -> {
            if (!observer.isUnsubscribed()) {
                for (int i = 1; i < 5; i++) {
                    observer.onNext(i);
                }
                observer.onCompleted();
            }
        });

        ConnectableObservable<Integer> connectableObservable =  op1.publish();

        connectableObservable.subscribe(s -> System.out.println("From Subscriber 1: " + s));

        connectableObservable.subscribe(s -> System.out.println("From Subscriber 2: " + s));

        /**
         * The connect() method makes the Observable start emitting its values.
         *
         * At this point we only have 2 subscribers that will get the emitted values.
         * The third subscriber won't get any values because the Observable will already be completed when the the
         * third subscriber subscribes.
         */
        connectableObservable.connect();

        connectableObservable.subscribe(s -> System.out.println("From Subscriber 3: " + s));
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.reactivestreams.Subscriber;

import io.reactivex.rxjava3.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class RxReader {
    public static void main(String[] args) {
        read(new BufferedReader(new InputStreamReader(System.in)))
                .observeOn(Schedulers.trampoline())
                .blockingSubscribe(line ->  {
                    System.out.println( "out" + line);
                    if (line != null && line.equalsIgnoreCase("quit")) {
                        System.exit(0);
                    }
                });
    }

    public static Observable<String> read(BufferedReader reader) {
        return Observable.<String>create(subscriber -> {
            String line;
            while((line = reader.readLine()) != null) {
                subscriber.onNext(line);
                if (subscriber.isDisposed()) {
                    break;
                }
            }
            subscriber.onComplete();
        }).subscribeOn(Schedulers.io());
    }
}

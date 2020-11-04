package kr.dev.hoon.reactive_java.part2;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

public class RxSseEmitter extends SseEmitter {

    static final  long                    SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final Subscriber<Temperature> subscriber;

    public RxSseEmitter() {

        super(SSE_SESSION_TIMEOUT);

        this.subscriber = new Subscriber<Temperature>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Temperature temperature) {

                try {
                    RxSseEmitter.this.send(temperature);
                } catch (Exception e) {
                    unsubscribe();
                }
            }
        };

        onCompletion(subscriber::unsubscribe);
        onTimeout(subscriber::unsubscribe);
    }

    Subscriber<Temperature> getSubscriber() {
        return subscriber;
    }
}

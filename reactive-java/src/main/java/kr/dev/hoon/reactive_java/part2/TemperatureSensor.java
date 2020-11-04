package kr.dev.hoon.reactive_java.part2;

import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class TemperatureSensor {

    private final Random rnd = new Random();

    private final Observable<Temperature> dataStream =
            Observable.range(0, Integer.MAX_VALUE)
                    .concatMap(tick -> Observable // 변환
                            .just(tick) // 새로운 stream 생성
                            .delay(rnd.nextInt(5000), TimeUnit.MILLISECONDS) // 무작위 delay
                            .map(tickValue -> this.probe())) // mapping
                    .publish() // 발행 , Broad Casting
                    .refCount(); // 구독자가 존재할때만 센서를 탐색한다.

    private Temperature probe() {

        return new Temperature(16 + rnd.nextGaussian() * 10);
    }

    public Observable<Temperature> temperatureStream() {

        return dataStream;
    }
}

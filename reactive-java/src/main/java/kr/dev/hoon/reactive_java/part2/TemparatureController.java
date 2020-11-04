package kr.dev.hoon.reactive_java.part2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TemparatureController {

    private TemperatureSensor temperatureSensor;

    public TemparatureController(TemperatureSensor temperatureSensor) {

        this.temperatureSensor = temperatureSensor;
    }

    @RequestMapping(value = "/temperature-stream", method = RequestMethod.GET)
    public SseEmitter events(HttpServletRequest request) {

        RxSseEmitter sseEmitter = new RxSseEmitter();

        temperatureSensor.temperatureStream()
                .subscribe(sseEmitter.getSubscriber());

        return sseEmitter;
    }

}

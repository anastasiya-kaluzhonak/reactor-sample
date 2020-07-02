package v1;

import model.Letter;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executors;

public class PechkinService {
    public Flux<Letter> sendLetters() {
        return Flux.<Letter>generate(sink -> {
            sink.next(new Letter("pechkin"));
        }).log("pechkin-")
        .publishOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(1, Executors.defaultThreadFactory())));
    }
}

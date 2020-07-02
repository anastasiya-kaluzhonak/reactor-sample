package v2;

import model.Letter;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executors;

public class PechkinService {

    // Letter's generator
    public Flux<Letter> sendLetters() {
        return Flux.<Letter>generate(sink -> {
            sink.next(new Letter("pechkin"));
        }).log("pechkin-");
    }
}

package v1;

import model.AnalyzedLetter;
import model.Letter;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BigBrother {

    public Flux<AnalyzedLetter> analyze(Flux<Letter> letterFlux) {
        return letterFlux
                // changed the demand to 256 (queue size) initially, then proactively prefetch 0.75*256 = 192 items
                .publishOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(4, Executors.defaultThreadFactory())))
                .log("bigbrother-")
                .map(letter -> new AnalyzedLetter("analyzed"))
                .doOnNext(letter -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                });
    }
}

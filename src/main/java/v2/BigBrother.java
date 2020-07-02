package v2;

import model.AnalyzedLetter;
import model.Letter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BigBrother {

    private Scheduler scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(4, Executors.defaultThreadFactory()));

    public Flux<AnalyzedLetter> analyze(Letter letter) {
        return Flux.just(letter)
                // changed the demand to 256 (queue size) initially, then proactively prefetch 0.75*256 = 192 items
                .publishOn(scheduler)
                .log("bigbrother-")
                .map(email -> new AnalyzedLetter("analyzed"))
                .doOnNext(email -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                });
    }
}

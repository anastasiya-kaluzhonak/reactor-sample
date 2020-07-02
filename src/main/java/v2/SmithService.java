package v2;

import model.AnalyzedLetter;
import model.ReactionForEmail;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SmithService {
    private Scheduler scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(2, Executors.defaultThreadFactory()));

    public Mono<ReactionForEmail> reactFor(AnalyzedLetter analyzedLetter) {
        return Mono.just(analyzedLetter)
                // changed the demand to 256 (queue size) initially, then proactively prefetch 0.75*256 = 192 items
                .publishOn(scheduler)
                .log("smith-")
                .map(letter -> new ReactionForEmail())
                .doOnNext(letter -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).then(Mono.empty());
    }
}

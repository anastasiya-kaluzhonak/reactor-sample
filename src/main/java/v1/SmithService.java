package v1;

import model.AnalyzedLetter;
import model.ReactionForEmail;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SmithService {

    public Flux<ReactionForEmail> reactFor(Flux<AnalyzedLetter> analyzedLetterFlux) {
        return analyzedLetterFlux
                // changed the demand to 256 (queue size) initially, then proactively prefetch 0.75*256 = 192 items
                .publishOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(2, Executors.defaultThreadFactory())))
                .log("smith-")
                .map(analyzedLetter -> new ReactionForEmail())
                .doOnNext(letter -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }
}

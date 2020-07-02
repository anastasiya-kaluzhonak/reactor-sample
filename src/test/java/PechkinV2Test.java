import v2.BigBrother;
import v2.PechkinService;
import v2.SmithService;

import java.util.Objects;

import org.junit.Test;

// FlatMap is used here
public class PechkinV2Test {

    @Test
    public void test() {
        PechkinService service = new PechkinService();
        BigBrother bigBrother = new BigBrother();
        SmithService smithService = new SmithService();

        service.sendLetters()
                //operator flatMap changes demand, please see more details in FluxFlatMap.class
                .flatMap(bigBrother::analyze)
                .filter(Objects::nonNull)
                .flatMap(smithService::reactFor)
                .blockLast();
    }
}

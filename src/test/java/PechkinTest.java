import v1.BigBrother;
import v1.PechkinService;
import v1.SmithService;

import org.junit.Test;

// Transform is used here
public class PechkinTest {

    @Test
    public void test() {
        PechkinService service = new PechkinService();
        BigBrother bigBrother = new BigBrother();
        SmithService smithService = new SmithService();

        service.sendLetters()
                .transform(bigBrother::analyze)
                .transform(smithService::reactFor)
                .blockLast();
    }
}

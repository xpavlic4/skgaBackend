package com.laurinka.skga.server.scratch;

import com.laurinka.skga.server.model.Result;
import java.util.logging.Logger;
import org.junit.Test;

/**
 */
public class SkgaHCPCheckerTest {
    @Test
    public void testQuery() throws Exception {
        final SkgaHCPChecker skgaHCPChecker = new SkgaHCPChecker();
        final Result query = skgaHCPChecker.query(new SkgaGolferNumber(9811));
        Logger.getAnonymousLogger().info(query.toString());
    }
}

package com.laurinka.skga.server.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 */
public class UtilsTest {
    @Test
    public void test() {
        String s = Utils.onlyNumber("asfd sdf1sdf 234");
        Assert.assertTrue("1234".equals(s));
    }
}

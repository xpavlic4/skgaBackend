package com.laurinka.skga.server.rest;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AbstractMemberResourceRestServiceTest {
    @Test
    public void testQuery() {
        AbstractMemberResourceRestService service = new AbstractMemberResourceRestService();
        String[] strings = {"a", "B"};
        String s = service.generateSql("select * from ", strings);
        assertTrue(s.contains(":name0"));
        assertTrue(s.contains(":name1"));
        assertFalse(s.contains(":name2"));
    }
}

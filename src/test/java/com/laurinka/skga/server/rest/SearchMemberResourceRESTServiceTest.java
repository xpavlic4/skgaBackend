package com.laurinka.skga.server.rest;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SearchMemberResourceRESTServiceTest {
    @Test
    public void splitNameAndSurname() {
        String[] split = AbstractMemberResourceRestService.REGEX.split("radim pavlicek");
        assertThat("" + Arrays.toString(split), split.length, is(2));
        assertThat(split[0], is("radim"));
    }
}

package com.laurinka.skga.server.rest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


/**
 * Just for testing and smoke tests.
 */
@Path("/test")
@RequestScoped
public class TestRESTService extends AbstractMemberResourceRestService {
    private static final Logger log = LogManager.getRootLogger();

    @GET
    @Path("/log")
    public String testLogging() {
        log.debug("I am a debug message");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK";
    }

}

package com.laurinka.skga.server.scratch;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class SyncJobBean implements Serializable {

	private static final long serialVersionUID = 3072889474365593550L;

	@Inject
	SyncJob job;
	
	public void run() throws IOException {
		job.fetchSnapshot();
	}
	
	@Produces @Named
	public String getTest() {
		return "wha";
	}
}

package com.laurinka.skga.server.scratch;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.laurinka.skga.server.model.Result;


public class CheckerTest {

	@Test
	public void testMyHandicap() throws IOException, ParseException {
		Result r  = new HCPChecker().query(new SkgaGolferNumber(9811));
		Assert.assertTrue("HCP should be 28.4!", !r.getHcp().isEmpty());
		Assert.assertEquals("SKGA Number should be 09811", r.getSkgaNr(),"09811");
		Assert.assertEquals("Club should be GC Pegas", r.getClub(), "GC Pegas");
		Assert.assertEquals("Club should be PAVLÍČEK Radim", r.getName(), "PAVLÍČEK Radim");

	}
	
	@Test
	public void testRandomHandicap() throws IOException, ParseException {
		Result r  = new HCPChecker().query(new SkgaGolferNumber(99999));
		Assert.assertTrue("Should be null as 1 not exists", r == null);
	}
	
	@Test
	public void testCharset() throws IOException {
		Result r  = new HCPChecker().query(new SkgaGolferNumber(1));
//		System.out.println(r);
	}

}

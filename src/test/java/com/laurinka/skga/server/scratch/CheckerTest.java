package com.laurinka.skga.server.scratch;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.Normalizer;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.laurinka.skga.server.model.Result;


public class CheckerTest {

	@Test
	public void testMyHandicap() throws IOException, ParseException {
		Result r  = new SkgaHCPChecker().query(new SkgaGolferNumber("09905"));
		assertTrue("HCP should be not empty!", !r.getHcp().isEmpty());
		Assert.assertEquals("SKGA Number should be 09905", r.getSkgaNr(),"09905");
		Assert.assertEquals("Club should be GC Pegas", r.getClub(), "GC Pegas");
		assertTrue(r.getName().contains("Lucia"));

	}
	
	@Test
	public void testRandomHandicap() throws IOException, ParseException {
		Result r  = new SkgaHCPChecker().query(new SkgaGolferNumber(99999));
		assertTrue("Should be null as 1 not exists", r == null);
	}

	@Test
	public void test2323Handicap() throws IOException, ParseException {
		Result r  = new SkgaHCPChecker().query(new SkgaGolferNumber(2323));
		//Assert.assertTrue("Should be null as 1 not exists", r == null);
	}
	@Test
	public void testCharset() throws IOException {
		Result r  = new SkgaHCPChecker().query(new SkgaGolferNumber(4));
		System.out.println(r);
		String s = Normalizer.normalize(r.getName(), Normalizer.Form.NFD);
		s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		System.out.println(s);
	}

}

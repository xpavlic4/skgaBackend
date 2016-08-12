package com.laurinka.skga.server.scratch;

import java.io.IOException;
import java.text.Normalizer;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.laurinka.skga.server.model.Result;


public class CgfCheckerTest {

	@Test
	public void testMyHandicap() throws IOException, ParseException {
		Result r  = new CgfHCPChecker().query(new CgfGolferNumber(985311));
		Assert.assertTrue("HCP should be 28.4!", !r.getHcp().isEmpty());
		Assert.assertEquals("CGF Number should be ", r.getSkgaNr(),"0985311");
		//Assert.assertEquals("Club should be ", r.getClub(), "Golfový Klub Brno-Žabovřesky");
		Assert.assertEquals("Club should be ", r.getName(), "MAJKUS Martin");

	}
	
	@Test
	public void testRandomHandicap() throws IOException, ParseException {
		Result r  = new CgfHCPChecker().query(new CgfGolferNumber(99999));
		Assert.assertTrue("Should be null as 1 not exists", r == null);
	}
	
	@Test
	public void testCharset() throws IOException {
		Result r  = new CgfHCPChecker().query(new CgfGolferNumber(985311));
		System.out.println(r);
		String s = Normalizer.normalize(r.getName(), Normalizer.Form.NFD);
		s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		System.out.println(s);
	}

}

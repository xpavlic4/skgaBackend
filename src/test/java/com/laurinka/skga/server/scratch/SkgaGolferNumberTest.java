package com.laurinka.skga.server.scratch;
import junit.framework.Assert;

import org.junit.Test;


public class SkgaGolferNumberTest {

	@Test
	public void test() {
		SkgaGolferNumber skgaGolferNumber = new SkgaGolferNumber(9811);
		String asString = skgaGolferNumber.asString();
		Assert.assertEquals("09811", asString);
	}

}

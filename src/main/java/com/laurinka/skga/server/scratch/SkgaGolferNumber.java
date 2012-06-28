package com.laurinka.skga.server.scratch;

public class SkgaGolferNumber {
	String nr; 
	public SkgaGolferNumber(int i ) {
		String s = "000000" + i;
		nr = s.substring(s.length() - 5,	 s.length());
	}
	public String asString() {
		return nr;
	}
	
}

package com.laurinka.skga.server.utils;

import java.text.Normalizer;

public class Utils {
	public static String stripAccents (String anStr) {
		if (null == anStr || "".equals(anStr))
			return "";
		String s = Normalizer.normalize(anStr, Normalizer.Form.NFD);
		s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return s;
	}
}
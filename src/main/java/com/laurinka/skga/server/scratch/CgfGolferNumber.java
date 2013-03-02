package com.laurinka.skga.server.scratch;

public class CgfGolferNumber {
    int i;

    public CgfGolferNumber(String tmp) {
        String anr = tmp.replaceFirst("^0+(?!$)", "");
        i = Integer.parseInt(anr);

    }

    public CgfGolferNumber(int ani) {
        i = ani;

    }

    public String asString() {
        String s = "0000000" + i;
        return s.substring(s.length() - 7, s.length());
    }

    public int asInt() {
        return i;
    }
}

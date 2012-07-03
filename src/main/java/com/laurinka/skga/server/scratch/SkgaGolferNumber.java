package com.laurinka.skga.server.scratch;

public class SkgaGolferNumber {
    int i;

    public SkgaGolferNumber(String anr) {
        i = Integer.getInteger(anr);

    }

    public SkgaGolferNumber(int ani) {
        i = ani;

    }

    public String asString() {
        String s = "000000" + i;
        return s.substring(s.length() - 5, s.length());
    }

    public int asInt() {
        return i;
    }
}

package com.laurinka.skga.server.scratch;

public class SkgaGolferNumber {
    String nr;
    int i;

    public SkgaGolferNumber(String anr) {
        nr = anr;

    }

    public SkgaGolferNumber(int ani) {
        i = ani;
        String s = "000000" + i;
        nr = s.substring(s.length() - 5, s.length());
    }

    public String asString() {
        return nr;
    }

    public int asInt() {
        return i;
    }
}

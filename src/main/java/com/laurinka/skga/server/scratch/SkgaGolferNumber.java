package com.laurinka.skga.server.scratch;

public class SkgaGolferNumber implements AsString {
    int i;

    public SkgaGolferNumber(String tmp) {
        String anr = tmp.replaceFirst("^0+(?!$)", "");
        i = Integer.parseInt(anr);

    }

    public SkgaGolferNumber(int ani) {
        i = ani;

    }

    @Override
    public String asString() {
        String s = "000000" + i;
        return s.substring(s.length() - 5, s.length());
    }

    public int asInt() {
        return i;
    }
}

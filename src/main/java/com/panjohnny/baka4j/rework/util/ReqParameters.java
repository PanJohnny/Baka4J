package com.panjohnny.baka4j.rework.util;

import java.util.HashMap;

public class ReqParameters {
    private final HashMap<String, String> params = new HashMap<>();

    public ReqParameters(String fromString) {
        aFromS(fromString);
    }

    public ReqParameters() {

    }

    public ReqParameters set(String key, String value) {
        params.put(key, value);
        return this;
    }

    public void aFromS(String s) {
        String[] p = s.split("&");
        for (String ps : p) {
            String[] args = ps.trim().split("=");
            switch (args.length) {
                case 2 -> params.put(args[0], args[1]);
                case 1 -> params.put(args[0], "");
                default -> {
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String k : params.keySet()) {
            s.append(k).append("=").append(params.get(k)).append("&");
        }

        return s.substring(0, s.length() - 1);
    }
}

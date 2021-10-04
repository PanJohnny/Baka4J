package com.panjohnny.baka4j;

public record BakaEndpoints() {
    public static final String LOGIN = "/api/login";
    public static final String HOMEWORKS = "/api/3/homeworks";
    public static final String HOMEWORKS_COUNT_ACTUAL = HOMEWORKS+"/count-actual";
}

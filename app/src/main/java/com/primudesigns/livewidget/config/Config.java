package com.primudesigns.livewidget.config;

public class Config {

    public static final String BASE_URL;
    private static final String PARAM = "-utf";

    static {
        BASE_URL = "https://www.hackerearth.com/api/events/upcoming/?format=json" + PARAM;
    }

}

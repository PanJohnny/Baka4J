package com.panjohnny.baka4j.rework.client;

import okhttp3.OkHttpClient;

public interface BakaClient {
    /**
     * Authorises the client
     */
    void authorise(String username, String password);

    /**
     * @return token that the client holds if authorised
     */
    String getToken();
    /**
     * @return refresh token that the client holds if authorised
     */
    String getRefreshToken();

    /**
     * Refreshes the token using refresh token
     */
    void refresh();

    /**
     * @return okhttp client that this object uses
     */
    OkHttpClient getOkHttpClient();

    /**
     * @return Specified school url
     */
    String getSchoolURL();

    /**
     * @return time in millis (System.currentTimeMillis()) that the access token expires
     */
    long getExpires();
}

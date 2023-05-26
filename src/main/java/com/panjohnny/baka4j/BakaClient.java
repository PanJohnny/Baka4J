package com.panjohnny.baka4j;

import com.panjohnny.baka4j.impl.BakaClientImpl;
import com.panjohnny.baka4j.util.AuthException;
import com.panjohnny.baka4j.v3.V3Client;
import com.panjohnny.baka4j.v3.V3WrapperClient;
import okhttp3.OkHttpClient;

public sealed interface BakaClient permits BakaClientImpl, V3Client, V3WrapperClient {
    /**
     * @param url url where your schools bakaláři is located at
     * @return Client for the v3 version of this project
     */
    static V3Client v3(String url) {
        return V3Client.getInstance(url);
    }

    /**
     * @param url url where your schools bakaláři is located at
     * @return Client for the v3 version of this project with methods wrapped using records
     * @implNote Not implemented fully yet
     */
    static V3WrapperClient v3Wrapper(String url) {
        return V3WrapperClient.getInstance(url);
    }

    /**
     * Authorises the client
     */
    void authorize(String username, String password) throws AuthException;

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
    void refresh() throws AuthException;

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

    /**
     * Authorize client with tokens and expires time
     * @param token token
     * @param refreshToken refresh token
     * @param expires time when the token expires (System.currentTimeMillis())
     */
    void authorize(String token, String refreshToken, long expires);

    /**
     * Enables jobs that refresh token in new thread periodically
     */
    void enableRefreshJobs();

    /**
     * Disables jobs that refresh token in new thread periodically
     */
    void disableRefreshJobs();
}

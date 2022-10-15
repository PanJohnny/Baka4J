package com.panjohnny.baka4j.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.panjohnny.baka4j.BakaClient;
import com.panjohnny.baka4j.util.ReqParameters;
import com.panjohnny.baka4j.v3.impl.V3ClientImpl;
import com.panjohnny.baka4j.v3.impl.V3WrapperClientImpl;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public sealed class BakaClientImpl implements BakaClient permits V3ClientImpl, V3WrapperClientImpl {
    private String token;
    private String refreshToken;
    private final String url;

    private long expiresIn;

    private final OkHttpClient httpClient;

    public static MediaType FORM = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");

    public BakaClientImpl(String url) {
        this(url, new OkHttpClient());
    }

    public BakaClientImpl(String url, OkHttpClient client) {
        this.httpClient = client;
        this.url = url;
    }

    @Override
    public void authorize(String username, String password) {
        Request request = post("/api/login", new ReqParameters("client_id=ANDR&grant_type=password").set("username", username).set("password", password)).build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IllegalArgumentException(Objects.requireNonNull(response.body()).string());
            }
            JsonObject json = JsonParser.parseString(Objects.requireNonNull(response.body()).string()).getAsJsonObject();
            refreshToken = json.get("refresh_token").getAsString();
            token = json.get("access_token").getAsString();
            expiresIn = System.currentTimeMillis() + json.get("expires_in").getAsLong() * 1000;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public void refresh() {
        Request request = post("/api/login", new ReqParameters("client_id=ANDR&grant_type=refresh_token").set("refresh_token", refreshToken)).build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IllegalArgumentException(Objects.requireNonNull(response.body()).string());
            }
            JsonObject json = JsonParser.parseString(Objects.requireNonNull(response.body()).string()).getAsJsonObject();
            refreshToken = json.get("refresh_token").getAsString();
            token = json.get("access_token").getAsString();
            expiresIn = System.currentTimeMillis() + json.get("expires_in").getAsLong() * 1000;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OkHttpClient getOkHttpClient() {
        return httpClient;
    }

    @Override
    public String getSchoolURL() {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    @Override
    public long getExpires() {
        return expiresIn;
    }

    protected Request.Builder get(String endpoint, ReqParameters parameters) {
        Request.Builder builder = new Request.Builder().header("Content-Type", "application/x-www-form-urlencoded").get();
        String u = getSchoolURL() + endpoint;
        if (parameters != null) {
            u += "?" + parameters;
        }

        if (token != null) {
            // add authorization header
            builder.header("Authorization", "Bearer " + token);
        }

        return builder.url(u);
    }

    @SuppressWarnings("SameParameterValue")
    protected Request.Builder post(String endpoint, ReqParameters parameters) {
        Request.Builder builder = new Request.Builder().header("Content-Type", "application/x-www-form-urlencoded");
        String u = getSchoolURL() + endpoint;

        if (token != null) {
            // add authorization header
            builder.header("Authorization", "Bearer " + token);
        }

        return builder.url(u).post(RequestBody.create(parameters.toString(), FORM));
    }



    protected Request.Builder get(String endpoint) {
        return get(endpoint, null);
    }

    @Override
    public void authorize(String token, String refreshToken, long expires) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expires;
    }
}

package com.panjohnny.baka4j.v3.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.panjohnny.baka4j.impl.BakaClientImpl;
import com.panjohnny.baka4j.v3.V3Client;
import com.panjohnny.baka4j.v3.V3WrapperClient;
import com.panjohnny.baka4j.v3.records.ApiInfo;
import com.panjohnny.baka4j.v3.records.GDPRCommissioner;
import com.panjohnny.baka4j.v3.records.StudentAbsence;
import com.panjohnny.baka4j.util.RecordTypeJavaSuitableAdapterFactory;
import com.panjohnny.baka4j.util.RestAction;
import okhttp3.OkHttpClient;

public final class V3WrapperClientImpl extends BakaClientImpl implements V3WrapperClient {
    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new RecordTypeJavaSuitableAdapterFactory()).create();
    private final V3Client basicClient;

    public V3WrapperClientImpl(String url) {
        super(url);
        this.basicClient = new V3ClientImpl(url, getOkHttpClient());
    }

    public V3WrapperClientImpl(String url, OkHttpClient client) {
        super(url, client);
        this.basicClient = new V3ClientImpl(url, client);
    }

    @Override
    public void authorize(String username, String password) {
        super.authorize(username, password);
        this.basicClient.authorize(getToken(), getRefreshToken(), getExpires());
    }

    @Override
    public void authorize(String token, String refreshToken, long expires) {
        super.authorize(token, refreshToken, expires);
        this.basicClient.authorize(getToken(), getRefreshToken(), getExpires());
    }

    @Override
    public void refresh() {
        super.refresh();
        this.basicClient.authorize(getToken(), getRefreshToken(), getExpires());
    }

    @Override
    public RestAction<ApiInfo[]> apis() {
        return new RestAction<>() {
            @Override
            protected ApiInfo[] handle() throws Throwable {
                JsonArray object = basicClient.apis().queue();

                return gson.fromJson(object, ApiInfo[].class);
            }
        };
    }

    @Override
    public RestAction<ApiInfo> apiInfo() {
        return new RestAction<>() {
            @Override
            protected ApiInfo handle() throws Throwable {
                JsonObject object = basicClient.apiInfo().queue();

                return gson.fromJson(object, ApiInfo.class);
            }
        };
    }

    @Override
    public RestAction<StudentAbsence> absence() {
        return new RestAction<>() {
            @Override
            protected StudentAbsence handle() throws Throwable {
                JsonObject object = basicClient.absence().queue();

                return gson.fromJson(object, StudentAbsence.class);
            }
        };
    }

    @Override
    public RestAction<GDPRCommissioner[]> gdprCommissioners() {
        return new RestAction<>() {
            @Override
            protected GDPRCommissioner[] handle() throws Throwable {
                JsonObject object = basicClient.gdprCommissioners().queue();

                return gson.fromJson(object.get("Commissioners").getAsJsonArray(), GDPRCommissioner[].class);
            }
        };
    }
}

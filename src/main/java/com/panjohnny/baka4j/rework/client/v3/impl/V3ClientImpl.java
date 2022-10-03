package com.panjohnny.baka4j.rework.client.v3.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.panjohnny.baka4j.rework.client.impl.BakaClientImpl;
import com.panjohnny.baka4j.rework.client.v3.V3Client;
import com.panjohnny.baka4j.rework.util.ReqParameters;
import com.panjohnny.baka4j.rework.util.RestAction;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

import static com.panjohnny.baka4j.rework.util.BakaChecks.checkCode;

public class V3ClientImpl extends BakaClientImpl implements V3Client {
    public V3ClientImpl(String url) {
        super(url);
    }

    @SuppressWarnings("unused")
    public V3ClientImpl(String url, OkHttpClient client) {
        super(url, client);
    }

    private <T extends JsonElement> RestAction<T> quickGet(String path, Function<JsonElement, T> transform) {
        return new RestAction<>() {
            @Override
            protected T handle() throws Throwable {
                Request request = get(path).build();

                Response response = getOkHttpClient().newCall(request).execute();
                checkCode(response);

                return transform.apply(JsonParser.parseString(Objects.requireNonNull(response.body()).string()));
            }
        };
    }

    private <T extends JsonElement> RestAction<T> quickGet(String path, Function<JsonElement, T> transform, ReqParameters parameters) {
        return new RestAction<>() {
            @Override
            protected T handle() throws Throwable {
                Request request = get(path, parameters).build();

                Response response = getOkHttpClient().newCall(request).execute();
                checkCode(response);

                return transform.apply(JsonParser.parseString(Objects.requireNonNull(response.body()).string()));
            }
        };
    }

    @Override
    public RestAction<JsonArray> apis() {
        return quickGet("/api", JsonElement::getAsJsonArray);
    }

    @Override
    public RestAction<JsonObject> apiInfo() {
        return quickGet("/api/3", JsonElement::getAsJsonObject);
    }

    @Override
    public RestAction<JsonObject> absence() {
        return quickGet("/api/3/absence/student", JsonElement::getAsJsonObject);
    }

    @Override
    public Events events(Date date) {
        return events(Events.FROM_FORMAT.format(date));
    }

    @Override
    public Events events(String from) {
        ReqParameters parameters = new ReqParameters();
        if (!from.isEmpty()) {
            parameters.set("from", from);
        } else {
            parameters = null;
        }
        ReqParameters finalParameters = parameters;
        return new Events() {
            @Override
            public RestAction<JsonObject> all() {
                return quickGet("/api/3/events", JsonElement::getAsJsonObject, finalParameters);
            }

            @Override
            public RestAction<JsonObject> public_() {
                return quickGet("/api/3/events/public", JsonElement::getAsJsonObject, finalParameters);
            }

            @Override
            public RestAction<JsonObject> my() {
                return quickGet("/api/3/events/my", JsonElement::getAsJsonObject, finalParameters);
            }
        };
    }

    @Override
    public Events events() {
        return events("");
    }
}

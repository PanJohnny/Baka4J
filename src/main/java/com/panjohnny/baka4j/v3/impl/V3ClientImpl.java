package com.panjohnny.baka4j.v3.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.panjohnny.baka4j.impl.BakaClientImpl;
import com.panjohnny.baka4j.v3.V3Client;
import com.panjohnny.baka4j.util.ReqParameters;
import com.panjohnny.baka4j.util.RestAction;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

import static com.panjohnny.baka4j.util.BakaChecks.checkCode;

public final class V3ClientImpl extends BakaClientImpl implements V3Client {

    private final RestAction<JsonArray> apisRestAction = quickGet("/api", JsonElement::getAsJsonArray);
    private final RestAction<JsonObject> apiInfoRestAction = quickGet("/api/3", JsonElement::getAsJsonObject);
    private final RestAction<JsonObject> absenceRestAction = quickGet("/api/3/absence/student", JsonElement::getAsJsonObject);
    private final RestAction<JsonObject> gdprCommissionersRestAction = quickGet("/api/3/gdpr/commissioners", JsonElement::getAsJsonObject);
    private final Assignments assignments = new Assignments() {
        @Override
        public RestAction<JsonObject> get() {
            return quickGet("/api/3/homeworks", JsonElement::getAsJsonObject);
        }

        @Override
        public RestAction<JsonObject> get(Date from) {
            return get(Events.FROM_FORMAT.format(from));
        }

        @Override
        public RestAction<JsonObject> get(String from) {
            return quickGet("/api/3/homeworks", JsonElement::getAsJsonObject, new ReqParameters().set("from", from));
        }

        @Override
        public RestAction<Integer> countActual() {
            return quickGet("/api/3/homeworks/count-actual", JsonElement::getAsInt);
        }
    };
    private final Komens komensRecieved = komens("/recieved");
    private final Komens komensNoticeboard = komens("/noticeboard");
    private final Marks marks = new Marks() {
        @Override
        public RestAction<JsonObject> get() {
            return quickGet("/api/3/marks", JsonElement::getAsJsonObject);
        }

        @Override
        public RestAction<Integer> countNew() {
            return quickGet("/api/3/marks/count-new", JsonElement::getAsInt);
        }

        @Override
        public RestAction<JsonObject> final_() {
            return quickGet("/api/3/marks/final", JsonElement::getAsJsonObject);
        }

        @Override
        public RestAction<JsonObject> measures() {
            return quickGet("/api/3/marks/measures", JsonElement::getAsJsonObject);
        }

        @Override
        public RestAction<JsonObject> whatIf(JsonArray object) {
            return quickGet("/api/3/marks/what-if", JsonElement::getAsJsonObject, ReqParameters.raw(object));
        }
    };

    public V3ClientImpl(String url) {
        super(url);
    }

    @SuppressWarnings("unused")
    public V3ClientImpl(String url, OkHttpClient client) {
        super(url, client);
    }

    private <T> RestAction<T> quickGet(String path, Function<JsonElement, T> transform) {
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

    private <T> RestAction<T> quickGet(String path, Function<JsonElement, T> transform, ReqParameters parameters) {
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
        return apisRestAction;
    }

    @Override
    public RestAction<JsonObject> apiInfo() {
        return apiInfoRestAction;
    }

    @Override
    public RestAction<JsonObject> absence() {
        return absenceRestAction;
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

    @Override
    public RestAction<JsonObject> gdprCommissioners() {
        return gdprCommissionersRestAction;
    }

    @Override
    public Assignments assignments() {
        return assignments;
    }

    private Komens komens(String suffix) {
        return new Komens() {
            @Override
            public RestAction<JsonObject> json() {
                return quickGet("/api/3/komens/messages"+suffix, JsonElement::getAsJsonObject);
            }

            // TODO fix
            @Override
            public RestAction<Integer> unread() {
                return quickGet("/api/3/komens/messages"+suffix+"/unread", JsonElement::getAsInt);
            }
        };
    }

    @Override
    public Komens komensRecieved() {
        return komensRecieved;
    }

    @Override
    public Komens komensNoticeboard() {
        return komensNoticeboard;
    }

    @Override
    public Marks marks() {
        return marks;
    }


}

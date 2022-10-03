package com.panjohnny.baka4j.rework.client.v3;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.panjohnny.baka4j.rework.client.BakaClient;
import com.panjohnny.baka4j.rework.client.v3.impl.V3ClientImpl;
import com.panjohnny.baka4j.rework.util.RestAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface V3Client extends BakaClient {
    static V3Client getInstance(String url) {
        return new V3ClientImpl(url);
    }
    /**
     * <h1>/api</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/API_info.md">...</a>
     */
    RestAction<JsonArray> apis();

    /**
     * <h1>/api/3</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/API_info.md">...</a>
     */
    RestAction<JsonObject> apiInfo();

    /**
     * <h1>/api/3/absence/student</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/absence.md">...</a>
     */
    RestAction<JsonObject> absence();

    /**
     * <h1>/api/3/events/*</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/events.md">...</a>
     */
    Events events();

    /**
     * <h1>/api/3/events/*?from=yyyy-MM-dd</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/events.md">...</a>
     * @param from date from the events will be requested
     */
    Events events(Date from);

    /**
     * <h1>/api/3/events/*?from=yyyy-MM-dd</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/events.md">...</a>
     * @param from date from the events will be requested, format: YYYY-MM-dd
     * @see Events#FROM_FORMAT
     */
    Events events(String from);

    /**
     * <h1>/api/3/events/*</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/events.md">...</a>
     */
    interface Events {
        DateFormat FROM_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        /**
         * <h1>/api/3/events</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/events.md">...</a>
         */
        RestAction<JsonObject> all();

        /**
         * <h1>/api/3/events/public</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/events.md">...</a>
         */
        RestAction<JsonObject> public_();

        /**
         * <h1>/api/3/events/my</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/events.md">...</a>
         */
        RestAction<JsonObject> my();
    }
}

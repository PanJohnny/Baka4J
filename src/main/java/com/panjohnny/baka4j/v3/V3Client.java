package com.panjohnny.baka4j.v3;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.panjohnny.baka4j.BakaClient;
import com.panjohnny.baka4j.v3.impl.V3ClientImpl;
import com.panjohnny.baka4j.util.RestAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public sealed interface V3Client extends BakaClient permits V3ClientImpl {
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

    /**
     * <h1>/api/3/gdpr/commissioners</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/gdpr.md">...</a>
     * @apiNote May be forbidden for "student" profiles
     */
    RestAction<JsonObject> gdprCommissioners();

    /**
     * <h1>/api/3/homeworks/*</h1>
     * @apiNote Homework is uncountable
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework.md">...</a>
     */
    Assignments assignments();

    /**
     * <h1>/api/3/homeworks/*</h1>
     * @apiNote Homework is uncountable
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework.md">...</a>
     */
    interface Assignments {

        /**
         * <h1>/api/3/homework</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework.md">...</a>
         */
        RestAction<JsonObject> get();

        /**
         * <h1>/api/3/homework?from=yyyy-MM-dd</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework.md">...</a>
         * @param from date from the events will be requested
         */
        RestAction<JsonObject> get(String from);

        /**
         * <h1>/api/3/homework?from=yyyy-MM-dd</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework.md">...</a>
         * @param from date from the events will be requested
         */
        RestAction<JsonObject> get(Date from);

        /**
         * <h1>/api/3/homeworks/count-actual</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework_new.md">...</a>
         */
        RestAction<Integer> countActual();
    }

    /**
     * <h1>/api/3/komens/messages/recieved/*</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/komens.md">...</a>
     * @see #komensNoticeboard()
     */
    Komens komensRecieved();

    /**
     * <h1>/api/3/komens/messages/noticeboard/*</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/komens.md">...</a>
     * @see #komensRecieved()
     */
    Komens komensNoticeboard();

    /**
     * <h1>/api/3/komens/*</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/komens.md">...</a>
     */
    interface Komens {
        /**
         * <h1>/api/3/komens/messages/[type]
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/komens.md">...</a>
         */
        RestAction<JsonObject> json();
        /**
         * <h1>/api/3/komens/messages/[type]/unread
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/komens.md">...</a>
         */
        RestAction<Integer> unread();
    }

    /**
     * <h1>/api/3/marks/*</h1>
     * @see Marks#get() 
     * @see Marks#countNew() 
     * @see Marks#final_() 
     * @see Marks#measures()
     * @see Marks#whatIf(JsonArray)
     */
    Marks marks();

    /**
     * <h1>/api/3/marks/*</h1>
     */
    interface Marks {
        /**
         * <h1>/api/3/marks</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/marks.md">...</a>
         */
        RestAction<JsonObject> get();

        /**
         * <h1>/api/3/marks/count-new</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/marks_new.md">...</a>
         */
        RestAction<Integer> countNew();

        /**
         * <h1>/api/3/marks/final</h1>
         * All certificates
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/marks_final.md">...</a>
         */
        RestAction<JsonObject> final_();

        /**
         * <h1>/api/3/marks/measures</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/marks_measures.md">...</a>
         */
        RestAction<JsonObject> measures();

        /**
         * <h1>/api/3/marks/what-if</h1>
         * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/whatif.md">...</a>
         * <h2>How to</h2>
         * The object provided is copy from {@link #get()} and after that marks with ID null.
         * @param object modified copy from {@link #get()}
         */
        RestAction<JsonObject> whatIf(JsonArray object);
    }
}

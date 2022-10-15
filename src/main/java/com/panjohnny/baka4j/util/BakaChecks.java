package com.panjohnny.baka4j.util;

import okhttp3.Response;

public final class BakaChecks {
    public static void checkAuth(Response response) {
        if (response.code() == 401) {
            throw new IllegalStateException("401 Unauthorized");
        }
    }

    public static void checkMethod(Response response) {
        if (response.code() == 405) {
            throw new IllegalStateException("405 Method Not Allowed");
        }
    }

    public static void checkBadRequest(Response response) {
        if (response.code() == 400) {
            throw new IllegalStateException("400 Bad Request");
        }
    }

    public static void checkForbidden(Response response) {
        if (response.code() == 403) {
            throw new IllegalStateException("400 Forbidden");
        }
    }

    public static void checkCode(Response response) {
        if (response.code() == 200)
            return;
        checkAuth(response);
        checkMethod(response);
        checkBadRequest(response);
        checkForbidden(response);
    }
}

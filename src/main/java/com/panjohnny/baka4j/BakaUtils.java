package com.panjohnny.baka4j;

import com.google.gson.*;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class BakaUtils {
    public static final Header CONTENT_TYPE = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");

    public static JsonElement post(String endpoint, String body, Header... headers) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(endpoint);
        for (Header h:
             headers) {
            post.addHeader(h);
        }
        try {
            post.setEntity(new StringEntity(body));
            HttpResponse response = client.execute(post);
            String a = EntityUtils.toString(response.getEntity());
            return JsonParser.parseString(a);
        } catch (IOException e) {
            System.err.println("error occurred");
            return JsonParser.parseString("{\"message\":\"Error when issuing post request\",\"exception\":\""+e+"\"}");
        }

    }

    public static JsonElement get(String endpoint, Header... headers) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(endpoint);
        for (Header h:
                headers) {
            get.addHeader(h);
        }
        try {
            CloseableHttpResponse response = client.execute(get);
            String a = EntityUtils.toString(response.getEntity());
            return JsonParser.parseString(a);
        } catch (IOException ex) {
            System.err.println("error occurred");
            return JsonParser.parseString("{\"message\":\"Error when issuing get request\",\"exception\":\""+ex+"\"}");
        }
    }

}

package com.panjohnny.baka4j;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.panjohnny.baka4j.elements.BasicElement;
import com.panjohnny.baka4j.elements.Homework;
import com.panjohnny.baka4j.elements.Mark;
import com.panjohnny.baka4j.elements.MarkContainer;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A main class that you will use most of the time
 * @see <a href="https://github.com/bakalari-api/bakalari-api-v3">Bakaláři api v3 github</a>
 * @author PanJohnny
 */
public class BakaClient {
    private final String schoolEndpoint, username, password;
    protected String token;
    protected Header authHeader;
    private Thread updater;
    private boolean autoU;
    /**
     * Class that will let you get acces to the api
     * @param schoolEndpoint endpoint to the bakaláři app like bakalari.yourschoolurl.com
     * @param username your username
     * @param password your password
     */
    public BakaClient(String schoolEndpoint, String username, String password) {

        this.password = password;
        this.username = username;
        this.schoolEndpoint = schoolEndpoint.endsWith("/")?schoolEndpoint.substring(0, schoolEndpoint.length()-1):schoolEndpoint;
        updater = new Thread(() -> {
            while(autoU) {
                requestAuthorization();
                try {
                    Thread.sleep((long) 3.3e+6);
                } catch (InterruptedException e) {
                    System.err.println("Auto-token-updater error");
                    e.printStackTrace();
                }
            }
        }, "Token-auto-updater thread");
        requestAuthorization();
    }

    /**
     * Starts token-auto-updater
     */
    public synchronized BakaClient startAutoToken() {
        autoU = true;
        updater.start();
        return this;
    }

    /**
     * Stops token-auto-updater
     */
    public synchronized BakaClient stopAutoToken() {
        autoU = false;
        try {
            updater.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
    /**
     * Retrieves secret authorization token from Bakaláři
     * <a href="https://bakalari.gymlit.cz/bakaweb/api/login/">Login endpoint</a>
     * @see BakaEndpoints#LOGIN
     * @apiNote This method is automatically called when using built-in token updater
     */
    public void requestAuthorization() {
        JsonElement login = BakaUtils.post(schoolEndpoint+BakaEndpoints.LOGIN, "client_id=ANDR&grant_type=password&username="+username+"&password="+password, BakaUtils.CONTENT_TYPE);
        JsonObject json = login.getAsJsonObject();

        if(!json.has("access_token")) {
            System.out.println("There are problems with your schools servers or there was significant change in api");
            System.exit(100);
        }
        token = json.get("access_token").getAsString();
        authHeader = new BasicHeader("Authorization", "Bearer "+token);
    }

    /**
     * Retrieves list of homeworks from past 14 days to tomorrow
     * <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework.md">Homework documentation</a>
     * @see Homework
     */
    public List<Homework> requestHomeworks() {
        ArrayList<Homework> homeworks = new ArrayList<>();
        JsonArray objects = BakaUtils.get(schoolEndpoint+BakaEndpoints.HOMEWORKS, BakaUtils.CONTENT_TYPE, authHeader).getAsJsonObject().get("Homeworks").getAsJsonArray();

        for(JsonElement el : objects) {
            JsonObject t = el.getAsJsonObject();
            homeworks.add(
                    new Homework(
                            t.get("ID").getAsString(),
                            t.get("DateStart").getAsString(),
                            t.get("DateEnd").getAsString(),
                            t.get("Content").getAsString(),
                            t.get("Notice").getAsString(),
                            t.get("Done").getAsBoolean(),
                            t.get("Closed").getAsBoolean(),
                            t.get("Electronic").getAsBoolean(),
                            BasicElement.parse(t.get("Class").getAsJsonObject()),
                            BasicElement.parse(t.get("Group").getAsJsonObject()),
                            BasicElement.parse(t.get("Subject").getAsJsonObject()),
                            BasicElement.parse(t.get("Teacher").getAsJsonObject())
                    )
            );
        }
        return homeworks;
    }

    /**
     * Retrieves list of homeworks for range
     * @param from the time stamp from when were homeworks submitted
     * @param to date when homeworks will be closed
     * <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework.md">Homework documentation</a>
     * @see Homework
     */
    public List<Homework> requestHomeworks(Date from, Date to) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        ArrayList<Homework> homeworks = new ArrayList<>();
        JsonArray objects = BakaUtils.get(schoolEndpoint+BakaEndpoints.HOMEWORKS
                + "?from="+format.format(from) + "&to="+format.format(to)
                , BakaUtils.CONTENT_TYPE, authHeader).getAsJsonObject().get("Homeworks").getAsJsonArray();

        for(JsonElement el : objects) {
            JsonObject t = el.getAsJsonObject();
            homeworks.add(
                    new Homework(
                            t.get("ID").getAsString(),
                            t.get("DateStart").getAsString(),
                            t.get("DateEnd").getAsString(),
                            t.get("Content").getAsString(),
                            t.get("Notice").getAsString(),
                            t.get("Done").getAsBoolean(),
                            t.get("Closed").getAsBoolean(),
                            t.get("Electronic").getAsBoolean(),
                            BasicElement.parse(t.get("Class").getAsJsonObject()),
                            BasicElement.parse(t.get("Group").getAsJsonObject()),
                            BasicElement.parse(t.get("Subject").getAsJsonObject()),
                            BasicElement.parse(t.get("Teacher").getAsJsonObject())
                    )
            );
        }
        return homeworks;
    }

    /**
     * Retrieves count of opened homeworks
     * @return how many opened homeworks are there
     */
    public int requestOpenedHomeworksCount() {
        return BakaUtils.get(schoolEndpoint+BakaEndpoints.HOMEWORKS_COUNT_ACTUAL, BakaUtils.CONTENT_TYPE, authHeader).getAsInt();
    }

    /**
     * Retrieves list of marks by subject in {@link MarkContainer}
     * @return List of containers of different marks by subject
     * <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/marks.md">Marks documentation</a>
     * @see MarkContainer
     */
    public List<MarkContainer> requestMarks() {
        JsonArray main = BakaUtils.get(schoolEndpoint+BakaEndpoints.MARKS, BakaUtils.CONTENT_TYPE, authHeader).getAsJsonObject().get("Subjects").getAsJsonArray();

        ArrayList<MarkContainer> containers = new ArrayList<>();
        for (int i = 0; i < main.size(); i++) {
            JsonObject curr = main.get(i).getAsJsonObject();

            List<Mark> marks = new ArrayList<>();
            JsonArray m = curr.get("Marks").getAsJsonArray();
            // marks
            for (int j = 0; j < m.size(); j++) {
                JsonObject ma = m.get(j).getAsJsonObject();

                Mark mark = new Mark(
                        ma.get("MarkDate").getAsString(),
                        ma.get("EditDate").getAsString(),
                        ma.get("Caption").getAsString(),
                        ma.get("Theme").getAsString(),
                        ma.get("MarkText").getAsString(),
                        ma.get("TeacherId").getAsString(),
                        ma.get("Type").getAsString(),
                        ma.get("TypeNote").getAsString(),
                        ma.get("Weight").getAsInt(),
                        ma.get("SubjectId").getAsString(),
                        ma.get("IsNew").getAsBoolean(),
                        ma.get("IsPoints").getAsBoolean(),
                        ma.get("CalculatedMarkText").getAsString(),
                        ma.get("ClassRankText")+"",
                        ma.get("Id").getAsString(),
                        ma.get("PointsText").getAsString(),
                        ma.get("MaxPoints").getAsInt()
                );

                marks.add(mark);
            }

            BasicElement subj = BasicElement.parse(curr.get("Subject").getAsJsonObject());
        
            MarkContainer mc = new MarkContainer(marks, subj,
                    curr.get("AverageText").getAsString());

            containers.add(mc);
        }

        return containers;
    }

    /**
     * Retrieves count of new marks that has not been requested yet. That means that if you have app on your phone this could not work.
     * @return number of new not viewed marks
     */
    public int requestNewMarksCount() {
        return BakaUtils.get(schoolEndpoint+BakaEndpoints.MARKS_COUNT_NEW, authHeader, BakaUtils.CONTENT_TYPE).getAsInt();
    }


}

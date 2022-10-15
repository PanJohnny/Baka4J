import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.panjohnny.baka4j.BakaClient;
import com.panjohnny.baka4j.v3.V3Client;
import junit.framework.TestCase;

import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Objects;

public class V3Test extends TestCase {
    static V3Client client;
    static {
        JsonObject login = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(V3WrapperTest.class.getResourceAsStream("login.json")))).getAsJsonObject();
        client = BakaClient.v3(login.get("url").getAsString());
        client.authorize(login.get("username").getAsString(), login.get("password").getAsString());
    }
    public void testEndpoint() {
        assertNotNull(client.apiInfo().queue());
        assertNotNull(client.apis().queue());
    }

    public void testGDPR() {
        assertNotNull(client.gdprCommissioners().queue());
    }

    public void testAbsence() {
        assertNotNull(client.absence().queue());
    }

    public void testEvents() {
        assertNotNull(client.events().all().queue());
        assertNotNull(client.events("2022-10-03").my().queue());
        assertNotNull(client.events(Calendar.getInstance().getTime()).public_().queue());
    }

    public void testRefresh() {
        client.refresh();
        assertNotNull(client.getRefreshToken());
        assertNotNull(client.getToken());
        assertNotSame(client.getExpires(), 0);
    }

    public void testAsyncActions() {
        client.apis().queue(TestCase::assertNotNull);

        client.apis().queue(TestCase::assertNotNull, Throwable::printStackTrace);
    }

    public void testAssignments() {
        assertNotNull(client.assignments().countActual().queue());
        assertNotNull(client.assignments().get().queue());
        assertNotNull(client.assignments().get("2022-08-05").queue());
        assertNotNull(client.assignments().get(Calendar.getInstance().getTime()).queue());
    }

    public void testKomens() {
//        assertNotNull(client.komensNoticeboard().json().queue());
        assertNotNull(client.komensNoticeboard().unread().queue());
        assertNotNull(client.komensRecieved().json().queue());
        assertNotNull(client.komensRecieved().unread().queue());
    }

    public void testGrades() {
        JsonObject object = client.marks().get().queue();
        assertNotNull(object);
        assertNotNull(client.marks().final_().queue());
        assertNotNull(client.marks().measures().queue());
        assertNotSame(client.marks().countNew().queue(), -1);
        // assertNotNull(client.marks().whatIf(object.get("Subjects").getAsJsonArray().get(0).getAsJsonObject().get("Marks").getAsJsonArray()).queue());
    }
}

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.panjohnny.baka4j.BakaClient;
import com.panjohnny.baka4j.v3.V3WrapperClient;
import junit.framework.TestCase;

import java.io.InputStreamReader;
import java.util.Objects;

public class V3WrapperTest extends TestCase {
    static V3WrapperClient client;
    static {
        JsonObject login = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(V3WrapperTest.class.getResourceAsStream("login.json")))).getAsJsonObject();
        client = BakaClient.v3Wrapper(login.get("url").getAsString());
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
}

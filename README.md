# Baka4J
### Note: JDK 17+ required currently doesn't have older version
A bakaláři api wrapper for java <br>
**This project is under development and currently doesn't have much stuff you can contribute to it** <br><br>
The main class is [BakaClient](src/main/java/com/panjohnny/baka4j/BakaClient.java)
1) create an instance of [BakaClient](src/main/java/com/panjohnny/baka4j/BakaClient.java)
```java
    BakaClient bc = new BakaClient("SCHOOL_URL", "USER_NAME", "PASSWORD");
```
2) configure auto token update you can start it using [BakaClient#startAutoToken](src/main/java/com/panjohnny/baka4j/BakaClient.java#L56)
3) You can start using my api enjoy

You can look at all endpoints that I currently use in [BakaEndpoints](src/main/java/com/panjohnny/baka4j/BakaEndpoints.java). This Project uses Record type objects to make the work easier.
## Homeworks
Homeworks are fetched using /3/homeworks endpoint. <br>
Here is an example:
```java
import com.panjohnny.baka4j.BakaClient;
import com.panjohnny.baka4j.elements.Homework;

public class Test {
    public static void main(String[] args) {
        BakaClient bc = new BakaClient("SCHOOL_URL", "USER_NAME", "PASSWORD");

        for (Homework h:
                bc.requestHomeworks()) {
            // checks if homework is closed, if it is it would skip it
            if(h.closed())
                continue;
            StringBuilder sb = new StringBuilder()
                    .append(h.clazz().abbrev()).append(" ")
                    .append(h.subject().abbrev()).append(": ")
                    .append(h.content())
                    .append(" -> due to: "+h.dateEnd());
            System.out.println(sb);
        }
    }
}
```
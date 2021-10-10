# Baka4J
<u>**A bakaláři api wrapper for java <br>**</u>
###**This project is possible because https://github.com/bakalari-api/bakalari-api-v3**
#### Note: JDK 17+ required currently doesn't have older version
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
Homeworks are fetched using `api/3/homeworks` endpoint. <br>
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
### Opened count
This is fetched from `api/3/homeworks/count-actual` <br>
Look at this example:
```java
public class Test {
    public static void main(String[] args) {
        BakaClient bc = new BakaClient("SCHOOL_URL", "USER_NAME", "PASSWORD");

        System.out.println(bc.requestOpenedHomeworksCount());
    }
}
```

## Marks
To request marks we use `/api/3/marks` <br>
Another example
```java
public class Test {
    public static void main(String[] args) {
        BakaClient bc = new BakaClient("SCHOOL_URL", "USER_NAME", "PASSWORD");
        for (MarkContainer mc:
             bc.requestMarks()) {
            StringBuilder sb = new StringBuilder()
                    .append("--------------")
                    .append(mc.subject().name())
                    .append("--------------")
                    .append('\n')
                    .append('|').append("   Average: ").append(mc.averageText())
                    .append('\n')
                    .append('|');
            for (Mark m:
                 mc.marks()) {
                sb.append("#######################");
                sb.append('\n').append('|');
                sb.append("   ").append("Caption: ").append(m.caption());
                sb.append('\n').append('|');
                sb.append("   ").append("Note: ").append(m.typeNote());
                sb.append('\n').append('|');
                sb.append("   ").append("Mark: ").append(m.markText());
                sb.append('\n').append('|');
                sb.append("   ").append("Weight: ").append(m.weight());
                sb.append('\n').append('|');
                sb.append("   ").append("Is new: ").append(m.isNew()).append('\n');
            }
            sb.append("\n______________________________________________");
            sb.append("\n\n\n\n\n\n\n\n");
            System.out.println(sb);

        }
    }
}
```

### Not viewed new marks
This shows the marks that hasn't been viewed yet. Fetched from `/api/3/marks/count-new`
```java
public class Test {
    public static void main(String[] args) {
        BakaClient bc = new BakaClient("SCHOOL_URL", "USER_NAME", "PASSWORD");

        System.out.println(bc.requestNewMarksCount());
    }
}
```
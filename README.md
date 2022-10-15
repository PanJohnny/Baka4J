# Baka4J
Baka4J is unofficial wrapper for [Bakaláři api v3](https://github.com/bakalari-api/bakalari-api-v3/). It provides a simple way to interact with some api endpoints.
The client is build using OKHttp client.

## How to use
This repository is not hosted anywhere. You need to build it yourself. This project has been created using openjdk 18.

You can start by referencing `BakaClient`. That is an interface having methods for instancing clients.

```java
import com.panjohnny.baka4j.BakaClient;

public class Test {
    {
        BakaClient client = BakaClient.v3(url) | BakaClient.v3Wrapper(url);
    }
}
```

This will create the BakaClient instance, but this will just provide you with options to login and nothing more. You should use the correct interface when creating object.

```java
import com.panjohnny.baka4j.BakaClient;
import com.panjohnny.baka4j.v3.V3Client;
import com.panjohnny.baka4j.v3.V3WrapperClient;

public class Test {
    {
        V3Client client = BakaClient.v3(url);
        V3WrapperClient wrapperClient = BakaClient.v3Wrapper(url);
    }
}
```

Now you can call the methods. This repository provides javadocs with links to [unofficial bakaláři api v3 docs](https://github.com/bakalari-api/bakalari-api-v3/).

## Unit testing
If you want to unit test this repo you can create file `login.json` in `src/test/resources/` with the following layout.
```json
{
  "username": "JohnDoe",
  "password": "ILikeCats",
  "url": "https://bakalari.example.com/"
}
```
Look into `src/test/java` to see unit tests.

## Contribute
Any contribution to this project is welcome. Create pull requests and issues. If you add new endpoint please provide an unit test.
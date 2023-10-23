# Baka4J
Baka4J je neoficiální wrapper pro [Bakaláři api v3](https://github.com/bakalari-api/bakalari-api-v3/). Pomocí OKHttp komunikuje s API Bakalářů.

## Použití 
Projekt si musíte buildnout, takže si ho stáhněte z GitHubu a pomocí mavenu compilujte do jaru.

Hlavní objekt je `BakaClient`. Je to interface, pomocí kterého můžete vytvořit svého clienta.

Clienti jsou 2:
 - v3 - standartní, vrací json
 - v3Wrapper - vrací java objekty

Prosím mějte na paměti, že jsou hotové jen některé endpointy, pokud nenaleznete co hledáte prosím otevřte issue nebo pull request s vaším řešením.

```java
import com.panjohnny.baka4j.BakaClient;

public class Test {
    {
        BakaClient clienti= BakaClient.v3(url) | BakaClient.v3Wrapper(url);
    }
}
```

Samo o sobě BakaClient nenabízí jiné metody než login, musíte tedy explicitně vybrat typ.

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

## Unit testy
Pokud chcete testovat, tak vytvořte soubor `login.json` v `src/test/resources/`, který bude vypadat takto:
```json
{
  "username": "JohnDoe",
  "password": "ILikeCats",
  "url": "https://bakalari.example.com/"
}
```
Všechny testy jsou v `src/test/java`.

## Přispívání 
Budu moc rád za jakýkoliv příspěvek do tohoto projektu.
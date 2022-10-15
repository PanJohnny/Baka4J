module Baka4J {
    requires com.google.gson;
    requires okhttp3;
    requires lombok;
    exports com.panjohnny.baka4j;
    exports com.panjohnny.baka4j.v3;
    exports com.panjohnny.baka4j.util;
    exports com.panjohnny.baka4j.v3.records;
    opens com.panjohnny.baka4j.v3.records to com.google.gson;
}
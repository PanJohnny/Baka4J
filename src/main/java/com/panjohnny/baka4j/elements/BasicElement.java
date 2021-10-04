package com.panjohnny.baka4j.elements;

import com.google.gson.JsonObject;

/**
 * A basic element
 * @param name name of element
 * @param abbrev short name of element like: Český Jazyk -> ČJ
 * @param ID id of element
 */
public record BasicElement(String ID, String abbrev, String name) {
    public static BasicElement parse(JsonObject obj) {
        return new BasicElement(obj.get("Id").getAsString(), obj.get("Abbrev").getAsString(), obj.get("Name").getAsString());
    }
}

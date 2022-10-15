package com.panjohnny.baka4j.v3.records;

import java.util.Objects;
import java.util.StringJoiner;

public record ApiInfo(String apiVersion, String applicationVersion, String baseUrl){
    @Override
    public String toString() {
        return new StringJoiner(", ", ApiInfo.class.getSimpleName() + "[", "]")
                .add("apiVersion='" + apiVersion + "'")
                .add("applicationVersion='" + applicationVersion + "'")
                .add("baseUrl='" + baseUrl + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiInfo apiInfo = (ApiInfo) o;

        if (!Objects.equals(apiVersion, apiInfo.apiVersion)) return false;
        if (!Objects.equals(applicationVersion, apiInfo.applicationVersion))
            return false;
        return Objects.equals(baseUrl, apiInfo.baseUrl);
    }

    @Override
    public int hashCode() {
        int result = apiVersion != null ? apiVersion.hashCode() : 0;
        result = 31 * result + (applicationVersion != null ? applicationVersion.hashCode() : 0);
        result = 31 * result + (baseUrl != null ? baseUrl.hashCode() : 0);
        return result;
    }
}
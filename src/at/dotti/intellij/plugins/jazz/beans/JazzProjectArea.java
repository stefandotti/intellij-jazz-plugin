package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JazzProjectArea {

    @JsonProperty
    private String name;
    @JsonProperty
    private String uuid;
    @JsonProperty
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

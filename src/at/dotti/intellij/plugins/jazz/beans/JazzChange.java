package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JazzChange {

    @JsonProperty
    private String path;
    @JsonProperty
    private JazzState state;
    @JsonProperty
    private String uuid;

    public String getPath() {
        return path;
    }

    public JazzState getState() {
        return state;
    }

    public String getUuid() {
        return uuid;
    }
}

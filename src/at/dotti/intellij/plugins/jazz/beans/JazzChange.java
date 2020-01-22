package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JazzChange {

    @JsonProperty
    private String path;
    @JsonProperty
    private JazzState state;
    @JsonProperty
    private String uuid;

    private JazzComponent component;

    public String getPath() {
        return path;
    }

    public JazzState getState() {
        return state;
    }

    public String getUuid() {
        return uuid;
    }

    public void setComponent(JazzComponent component) {
        this.component = component;
    }

    public JazzComponent getComponent() {
        return component;
    }
}

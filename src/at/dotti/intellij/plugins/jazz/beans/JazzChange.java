package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JazzChange {

    @JsonProperty("inaccessible-change")
    private boolean inaccessibleChange;
    @JsonProperty
    private List<Object> merges;
    @JsonProperty
    private String path;
    @JsonProperty
    private JazzState state;
    @JsonProperty("state-id")
    private String stateId;
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

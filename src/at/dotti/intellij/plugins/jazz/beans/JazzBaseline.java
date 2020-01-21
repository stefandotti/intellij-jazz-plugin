package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JazzBaseline {

    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String uuid;
}

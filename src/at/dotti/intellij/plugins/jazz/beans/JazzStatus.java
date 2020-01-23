package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JazzStatus {

    @JsonProperty
    private int code;
    @JsonProperty
    private String message;
    @JsonProperty
    private String severity;

}

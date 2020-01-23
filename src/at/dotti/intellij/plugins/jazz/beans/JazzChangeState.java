package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JazzChangeState {

    @JsonProperty
    private boolean active;
    @JsonProperty
    private boolean complete;
    @JsonProperty
    private boolean conflict;
    @JsonProperty
    private boolean current;
    @JsonProperty("current_merge_target")
    private boolean currentMergeTarget;
    @JsonProperty("has_source")
    private boolean hasSource;
    @JsonProperty("is_linked")
    private boolean isLinked;
    @JsonProperty("is_source")
    private boolean isSource;
    @JsonProperty("potential_conflict")
    private boolean potentialConflict;

}

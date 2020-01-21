package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JazzState {

    @JsonProperty
    private boolean add;
    @JsonProperty("content_change")
    private boolean contentChange;
    @JsonProperty
    private boolean delete;
    @JsonProperty
    private boolean move;
    @JsonProperty("property_change")
    private boolean propertyChange;

    public JazzStateEnum getState() {
        return JazzStateEnum.valueOf(this);
    }

    public boolean isAdd() {
        return add;
    }

    public boolean isContentChange() {
        return contentChange;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isMove() {
        return move;
    }

    public boolean isPropertyChange() {
        return propertyChange;
    }
}

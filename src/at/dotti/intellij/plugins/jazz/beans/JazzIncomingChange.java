package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JazzIncomingChange extends JazzBase {

    @JsonProperty
    private String author;
    @JsonProperty
    private List<JazzChange> changes;
    @JsonProperty
    private String comment;
    @JsonProperty
    private String modified;
    @JsonProperty
    private JazzChangeState state;

    private JazzComponent component;

    public void setComponent(JazzComponent component) {
        this.component = component;
    }

    public JazzComponent getComponent() {
        return component;
    }

    public List<JazzChange> getChanges() {
        return changes;
    }

    public String getComment() {
        return comment;
    }

    public String getAuthor() {
        return author;
    }

    public String getModified() {
        return modified;
    }
}

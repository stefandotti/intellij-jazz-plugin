package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class JazzWorkspace extends JazzBase {

    @JsonProperty
    private List<JazzComponent> components = new ArrayList<>();
    @JsonProperty("flow-target")
    private JazzFlowTarget flowTarget;
    @JsonProperty
    private String type;

    public List<JazzChange> getAllChanges() {
        List<JazzChange> changes = new ArrayList<>();
        for (JazzComponent component : this.components) {
            component.setWorkspace(this);
            for (JazzChange jazzChange : component.getUnresolved()) {
                jazzChange.setComponent(component);
                changes.add(jazzChange);
            }
        }
        return changes;
    }
}

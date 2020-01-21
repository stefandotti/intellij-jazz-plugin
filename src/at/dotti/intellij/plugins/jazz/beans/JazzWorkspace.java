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

    public List<JazzComponent> getComponents() {
        return components;
    }
}

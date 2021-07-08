package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class JazzComponent {

    @JsonProperty
    private JazzBaseline baseline;
    @JsonProperty("changesets-after-baseline")
    private boolean changesetsAfterBaseline;
    @JsonProperty("changesets-after-incoming-target-baseline")
    private boolean changesetsAfterIncomingTargetBaseline;
    @JsonProperty("flow-target")
    private JazzFlowTarget flowTarget;
    @JsonProperty("incoming-addition")
    private boolean incomingAddition;
    @JsonProperty("incoming-changes")
    private List<JazzIncomingChange> incomingChanges;
    @JsonProperty("incoming-deletion")
    private boolean incomingDeletion;
    @JsonProperty("incoming-replacement")
    private boolean incomingReplacement;
    @JsonProperty("incoming-target-baseline")
    private JazzIncomingTargetBaseline incomingTargetBaseline;
    @JsonProperty("incoming-baselines")
    private List<Object> incomingBaselines;
    @JsonProperty("is_comp_loaded")
    private boolean isCompLoaded;
    @JsonProperty
    private String name;
    @JsonProperty("outgoing-addition")
    private boolean outgoingAddition;
    @JsonProperty("outgoing-changes")
    private List<JazzOutgoingChange> outgoingChanges;
    @JsonProperty("outgoing-deletion")
    private boolean outgoingDeletion;
    @JsonProperty("outgoing-replacement")
    private boolean outgoingReplacement;
    @JsonProperty
    private List<Object> suspended;
    @JsonProperty
    private String type;
    @JsonProperty
    private List<JazzChange> unresolved = new ArrayList<>();
    @JsonProperty("local-conflicts")
    private List<Object> localConflicts;
    @JsonProperty
    private String uuid;

    private JazzWorkspace workspace;

    public String getName() {
        return name;
    }

    public boolean isCompLoaded() {
        return isCompLoaded;
    }

    public List<JazzChange> getUnresolved() {
        return unresolved;
    }

    public void setWorkspace(JazzWorkspace workspace) {
        this.workspace = workspace;
    }

    public JazzWorkspace getWorkspace() {
        return workspace;
    }

    public List<JazzOutgoingChange> getOutgoingChanges() {
        return outgoingChanges;
    }

    public List<JazzIncomingChange> getIncomingChanges() {
        return incomingChanges;
    }
}

package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JazzStatusObject {

    @JsonProperty
    private List<JazzStatus> status;

    @JsonProperty
    private List<JazzWorkspace> workspaces;

    public List<JazzChange> getChanges() {
        return workspaces != null ? workspaces.stream()
                .map(JazzWorkspace::getAllChanges)
                .flatMap(List::stream)
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    public List<JazzOutgoingChange> getOutgoingChange() {
        return workspaces != null ? workspaces.stream()
                .map(JazzWorkspace::getAllOutgoingChanges)
                .flatMap(List::stream)
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    public List<JazzWorkspace> getWorkspacesOutOfSync() {
        return workspaces.stream().filter(JazzWorkspace::isOutOfSync).collect(Collectors.toList());
    }

    public List<JazzWorkspace> getWorkspaces() {
        return workspaces;
    }
}

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

}

package at.dotti.intellij.plugins.jazz.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JazzStatusObject {

    @JsonProperty
    private List<JazzStatus> status;

    @JsonProperty
    private List<JazzWorkspace> workspaces;

    public List<JazzChange> getChanges() {
        return workspaces != null ? workspaces.stream()
                .map(JazzWorkspace::getComponents)
                .flatMap(List::stream)
                .map(JazzComponent::getUnresolved)
                .flatMap(List::stream)
                .collect(Collectors.toList()) : new ArrayList<>();
    }

}

package at.dotti.intellij.plugins.jazz.beans;

import java.util.ArrayList;
import java.util.List;

public class JazzProjectAreaList {

    private List<JazzProjectArea> projectAreas = new ArrayList<>();

    public List<JazzProjectArea> getProjectAreas() {
        return projectAreas;
    }

    public void setProjectAreas(List<JazzProjectArea> projectAreas) {
        this.projectAreas = projectAreas;
    }
}

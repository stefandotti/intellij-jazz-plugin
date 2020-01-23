package at.dotti.intellij.plugins.jazz.actions;

import at.dotti.intellij.plugins.jazz.vcs.JazzVcs;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.actions.StandardVcsGroup;
import org.jetbrains.annotations.Nullable;

public class JazzGroup extends StandardVcsGroup {
    @Override
    public AbstractVcs getVcs(Project project) {
        return JazzVcs.getInstance(project);
    }

    @Nullable
    @Override
    public String getVcsName(Project project) {
        return JazzVcs.getKey().getName();
    }
}

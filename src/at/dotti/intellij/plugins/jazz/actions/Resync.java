package at.dotti.intellij.plugins.jazz.actions;

import at.dotti.intellij.plugins.jazz.beans.JazzStatusObject;
import at.dotti.intellij.plugins.jazz.beans.JazzWorkspace;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Resync extends DumbAwareAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        JazzStatusObject state = null;
        try {
            state = JazzService.getInstance().status(e.getProject());
        } catch (JazzServiceException jazzServiceException) {
            jazzServiceException.printStackTrace();
        }
        List<JazzWorkspace> workspaces = state.getWorkspacesOutOfSync();

        ProgressManager.getInstance().run(new Task.Backgroundable(e.getProject(), "Jazz Resync") {
            public void run(ProgressIndicator indicator) {
                double dx = 100d / workspaces.size();
                int n = 0;
                for (JazzWorkspace workspace : workspaces) {
                    indicator.setText(workspace.getName());
                    indicator.setFraction((++n) * dx);
                    try {
                        JazzService.getInstance().resync(e.getProject(), workspace);
                    } catch (JazzServiceException jazzServiceException) {
                        jazzServiceException.printStackTrace();
                    }
                }
                VcsDirtyScopeManager.getInstance(e.getProject()).markEverythingDirty();
            }
        });
    }
}

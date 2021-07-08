package at.dotti.intellij.plugins.jazz.dialogs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentManager;
import org.jetbrains.annotations.NotNull;

public class RefreshWorkspaceAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() != null) {
            WorkspacePanel activeComponent = ChangesViewContentManager.getInstance(e.getProject()).getActiveComponent(WorkspacePanel.class);
            if (activeComponent != null) {
                activeComponent.refresh();
            }
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        if (e.getProject() != null) {
            WorkspacePanel activeComponent = ChangesViewContentManager.getInstance(e.getProject()).getActiveComponent(WorkspacePanel.class);
            if (activeComponent != null) {
                e.getPresentation().setEnabled(!activeComponent.isRefreshing());
            }
        }
    }
}

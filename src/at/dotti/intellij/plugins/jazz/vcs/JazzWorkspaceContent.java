package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.dialogs.WorkspacePanel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentManager;
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentProvider;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.util.NotNullFunction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.function.Supplier;

public class JazzWorkspaceContent implements ChangesViewContentProvider {

    private final Project project;

    public JazzWorkspaceContent(@NotNull Project project) {
        this.project = project;
    }

    public static void show(@NotNull Project project) {
        final ToolWindowManager manager = ToolWindowManager.getInstance(project);
        if (manager != null) {
            final ToolWindow window = manager.getToolWindow(ChangesViewContentManager.TOOLWINDOW_ID);
            if (window != null) {
                window.show(null);
                final ContentManager cm = window.getContentManager();
                final Content content = cm.findContent(getTabName());
                if (content != null) {
                    cm.setSelectedContent(content, true);
                }
            }
        }
    }

    @Override
    public JComponent initContent() {
        return new WorkspacePanel(this.project);
    }

    static final class VisibilityPredicate implements NotNullFunction<Project, Boolean> {

        @NotNull
        @Override
        public Boolean fun(Project project) {
            return ProjectLevelVcsManager.getInstance(project).checkVcsIsActive(JazzVcs.VCS_NAME);
        }
    }

    static final class DisplayNameSupplier implements Supplier<String> {

        @Override
        public String get() {
            return JazzBundle.message("toolwindow.working.copies.info.title");
        }
    }

    @NotNull
    public static String getTabName() {
        return JazzBundle.message("dialog.show.jazz.map.title");
    }
}

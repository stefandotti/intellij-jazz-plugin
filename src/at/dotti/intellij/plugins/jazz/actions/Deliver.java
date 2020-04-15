package at.dotti.intellij.plugins.jazz.actions;

import at.dotti.intellij.plugins.jazz.beans.JazzOutgoingChange;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import at.dotti.intellij.plugins.jazz.vcs.JazzCheckedinChange;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Deliver extends DumbAwareAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        Change[] changes = e.getData(VcsDataKeys.CHANGES);
        boolean enabled = changes != null && Arrays.stream(changes).allMatch(c -> c instanceof JazzCheckedinChange);
        e.getPresentation().setEnabled(enabled);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Change[] changes = e.getData(VcsDataKeys.CHANGES);
        if (changes == null) {
            return;
        }
        final Set<JazzOutgoingChange> uuids = new HashSet<>();
        for (Change change : changes) {
            if (change instanceof JazzCheckedinChange) {
                uuids.add(((JazzCheckedinChange) change).getOutgoingChange());
            }
        }
        ProgressManager.getInstance().run(new Task.Backgroundable(e.getProject(), "Jazz Deliver") {
            public void run(ProgressIndicator indicator) {
                double dx = 100d / uuids.size();
                int n = 0;
                for (JazzOutgoingChange uuid : uuids) {
                    indicator.setText(uuid.getName());
                    try {
                        System.out.println(JazzService.getInstance().deliver(e.getProject(), uuid));
                    } catch (JazzServiceException ex) {
                        ex.printStackTrace();
                    }
                    indicator.setFraction((++n)*dx);
                }
                VcsDirtyScopeManager.getInstance(e.getProject()).markEverythingDirty();
            }
        });
    }
}

package at.dotti.intellij.plugins.jazz.actions;

import at.dotti.intellij.plugins.jazz.beans.JazzOutgoingChange;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import at.dotti.intellij.plugins.jazz.vcs.JazzCheckedinChange;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.changes.Change;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Deliver extends DumbAwareAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        Change[] changes = e.getData(VcsDataKeys.CHANGES);
        boolean enabled = changes != null && Arrays.stream(changes).allMatch(c -> c instanceof JazzCheckedinChange);
        e.getPresentation().setEnabledAndVisible(enabled);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Change[] changes = e.getData(VcsDataKeys.CHANGES);
        if (changes == null) {
            return;
        }
        Set<JazzOutgoingChange> uuids = new HashSet<>();
        for (Change change : changes) {
            if (change instanceof JazzCheckedinChange) {
                uuids.add(((JazzCheckedinChange) change).getOutgoingChange());
            }
        }
        for (JazzOutgoingChange uuid : uuids) {
            try {
                JazzService.getInstance().deliver(e.getProject(), uuid);
            } catch (JazzServiceException ex) {
                ex.printStackTrace();
            }
        }
    }
}

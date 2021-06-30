package at.dotti.intellij.plugins.jazz.vcs;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.VcsCheckinHandlerFactory;
import org.jetbrains.annotations.NotNull;

public class JazzCheckinHandlerFactory extends VcsCheckinHandlerFactory {

    public JazzCheckinHandlerFactory() {
        super(JazzVcs.getKey());
    }

    @NotNull
    @Override
    protected CheckinHandler createVcsHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        return new JazzCheckinHandler(panel, commitContext);
    }

}

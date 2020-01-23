package at.dotti.intellij.plugins.jazz.vcs;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.CommitExecutor;
import com.intellij.openapi.vcs.changes.LocalCommitExecutor;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.util.PairConsumer;
import org.jetbrains.annotations.Nullable;

public class JazzCheckinHandler extends CheckinHandler {

    private final CheckinProjectPanel panel;

    public JazzCheckinHandler(CheckinProjectPanel checkinProjectPanel) {
        this.panel = checkinProjectPanel;
    }

    @Override
    public ReturnResult beforeCheckin(@Nullable CommitExecutor executor, PairConsumer<Object, Object> additionalDataConsumer) {
        if (executor instanceof LocalCommitExecutor) return ReturnResult.COMMIT;
        for (Change selectedChange : this.panel.getSelectedChanges()) {

        }
        return ReturnResult.COMMIT;
    }

}

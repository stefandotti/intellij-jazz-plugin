package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.beans.JazzChange;
import at.dotti.intellij.plugins.jazz.beans.JazzOutgoingChange;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ContentRevision;
import org.jetbrains.annotations.Nullable;

public class JazzCheckedinChange extends Change {
    private final JazzChange change;
    private final JazzOutgoingChange outgoingChange;

    public JazzCheckedinChange(@Nullable ContentRevision beforeRevision, @Nullable ContentRevision afterRevision, @Nullable FileStatus fileStatus, JazzChange changeChange, JazzOutgoingChange change) {
        super(beforeRevision, afterRevision, fileStatus);
        this.change = changeChange;
        this.outgoingChange = change;
    }

    public JazzChange getChange() {
        return change;
    }

    public JazzOutgoingChange getOutgoingChange() {
        return outgoingChange;
    }
}

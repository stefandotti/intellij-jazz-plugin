package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager;
import com.intellij.openapi.vcs.rollback.RollbackEnvironment;
import com.intellij.openapi.vcs.rollback.RollbackProgressListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JazzRollbackEnvironment implements RollbackEnvironment {
    private final JazzVcs vcs;

    public JazzRollbackEnvironment(JazzVcs jazzVcs) {
        this.vcs = jazzVcs;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @NotNull
    @Override
    public String getRollbackOperationName() {
        return "Revert";
    }

    @Override
    public void rollbackChanges(List<Change> changes, List<VcsException> exceptions, @NotNull RollbackProgressListener rollbackProgressListener) {
        try {
            String json = JazzService.getInstance().revert(this.vcs.getProject(), changes);
        }catch(JazzServiceException e) {
            exceptions.add(new VcsException(e));
        }
        VcsDirtyScopeManager.getInstance(this.vcs.getProject()).markEverythingDirty();
    }

    @Override
    public void rollbackMissingFileDeletion(List<FilePath> list, List<VcsException> list1, RollbackProgressListener rollbackProgressListener) {

    }

    @Override
    public void rollbackModifiedWithoutCheckout(List<VirtualFile> list, List<VcsException> list1, RollbackProgressListener rollbackProgressListener) {

    }

    @Override
    public void rollbackIfUnchanged(VirtualFile virtualFile) {

    }
}

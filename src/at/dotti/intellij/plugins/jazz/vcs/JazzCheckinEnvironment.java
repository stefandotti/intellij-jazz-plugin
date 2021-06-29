package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager;
import com.intellij.openapi.vcs.checkin.CheckinEnvironment;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JazzCheckinEnvironment implements CheckinEnvironment {
    private final JazzVcs vcs;

    public JazzCheckinEnvironment(JazzVcs jazzVcs) {
        this.vcs = jazzVcs;
    }

    @Nullable
    @Override
    public String getHelpId() {
        return null;
    }

    @Override
    public String getCheckinOperationName() {
        return "Checkin";
    }

    @Override
    public @Nullable List<VcsException> scheduleMissingFileForDeletion(@NotNull List<? extends FilePath> files) {
        return null;
    }

    @Override
    public @Nullable List<VcsException> scheduleUnversionedFilesForAddition(@NotNull List<? extends VirtualFile> files) {
        return null;
    }

    @Nullable
    @Override
    public List<VcsException> commit(@NotNull List<? extends Change> changes, @NotNull @NlsSafe String commitMessage, @NotNull CommitContext commitContext, @NotNull Set<? super @NlsContexts.DetailedDescription String> feedback) {
        // TODO: commit files to jazz
        List<VcsException> exceptions = new ArrayList<>();
        try {
            String json = JazzService.getInstance().checkin(this.vcs.getProject(), changes, commitMessage);
            System.out.println(json);
        }catch(JazzServiceException e) {
            exceptions.add(new VcsException(e));
        }
        VcsDirtyScopeManager.getInstance(this.vcs.getProject()).markEverythingDirty();
        return exceptions;
    }

    @Override
    public boolean isRefreshAfterCommitNeeded() {
        return false;
    }
}

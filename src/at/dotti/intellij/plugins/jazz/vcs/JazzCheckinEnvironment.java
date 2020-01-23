package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.CommitContext;
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

    @Nullable
    @Override
    public List<VcsException> scheduleMissingFileForDeletion(@NotNull List<FilePath> list) {
        // TODO: delete files from jazz
        return null;
    }

    @Nullable
    @Override
    public List<VcsException> scheduleUnversionedFilesForAddition(@NotNull List<VirtualFile> list) {
        // TODO: add files to jazz
        return null;
    }

    @Nullable
    @Override
    public List<VcsException> commit(@NotNull List<Change> changes, @NotNull String commitMessage, @NotNull CommitContext commitContext, @NotNull Set<String> feedback) {
        // TODO: commit files to jazz
        List<VcsException> exceptions = new ArrayList<>();
        try {
            String json = JazzService.getInstance().checkin(this.vcs.getProject(), changes, commitMessage);
            System.out.println(json);
        }catch(JazzServiceException e) {
            exceptions.add(new VcsException(e));
        }
        return exceptions;
    }

    @Override
    public boolean isRefreshAfterCommitNeeded() {
        return false;
    }
}

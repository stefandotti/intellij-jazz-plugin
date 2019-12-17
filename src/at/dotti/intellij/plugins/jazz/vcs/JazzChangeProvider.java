package at.dotti.intellij.plugins.jazz.vcs;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.ChangeListManagerGate;
import com.intellij.openapi.vcs.changes.ChangeProvider;
import com.intellij.openapi.vcs.changes.ChangelistBuilder;
import com.intellij.openapi.vcs.changes.VcsDirtyScope;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JazzChangeProvider implements ChangeProvider {
    @Override
    public void getChanges(@NotNull VcsDirtyScope vcsDirtyScope, @NotNull ChangelistBuilder changelistBuilder, @NotNull ProgressIndicator progressIndicator, @NotNull ChangeListManagerGate changeListManagerGate) throws VcsException {

    }

    @Override
    public boolean isModifiedDocumentTrackingRequired() {
        return false;
    }

    @Override
    public void doCleanup(List<VirtualFile> list) {

    }
}

package at.dotti.intellij.plugins.jazz.vcs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.history.*;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JazzHistoryProvider implements VcsHistoryProvider {
    private final JazzVcs vcs;

    public JazzHistoryProvider(JazzVcs jazzVcs) {
        this.vcs = jazzVcs;
    }

    @Override
    public VcsDependentHistoryComponents getUICustomization(VcsHistorySession vcsHistorySession, JComponent jComponent) {
        return null;
    }

    @Override
    public AnAction[] getAdditionalActions(Runnable runnable) {
        return new AnAction[0];
    }

    @Override
    public boolean isDateOmittable() {
        return false;
    }

    @Nullable
    @Override
    public String getHelpId() {
        return null;
    }

    @Nullable
    @Override
    public VcsHistorySession createSessionFor(FilePath filePath) throws VcsException {
        return null;
    }

    @Override
    public void reportAppendableHistory(FilePath filePath, VcsAppendableHistorySessionPartner vcsAppendableHistorySessionPartner) throws VcsException {

    }

    @Override
    public boolean supportsHistoryForDirectories() {
        return false;
    }

    @Nullable
    @Override
    public DiffFromHistoryHandler getHistoryDiffHandler() {
        return null;
    }

    @Override
    public boolean canShowHistoryFor(@NotNull VirtualFile virtualFile) {
        return false;
    }
}

package at.dotti.intellij.plugins.jazz.vcs;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.actions.VcsContext;
import com.intellij.openapi.vcs.actions.VcsContextFactory;
import com.intellij.openapi.vcs.changes.LocalChangeList;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public class JazzContextFactory implements VcsContextFactory {
    @NotNull
    @Override
    public VcsContext createCachedContextOn(@NotNull AnActionEvent anActionEvent) {
        return null;
    }

    @NotNull
    @Override
    public VcsContext createContextOn(@NotNull AnActionEvent anActionEvent) {
        return null;
    }

    @NotNull
    @Override
    public FilePath createFilePathOn(@NotNull VirtualFile virtualFile) {
        return null;
    }

    @NotNull
    @Override
    public FilePath createFilePathOn(@NotNull File file) {
        return null;
    }

    @NotNull
    @Override
    public FilePath createFilePathOn(@NotNull File file, boolean b) {
        return null;
    }

    @Override
    public @NotNull FilePath createFilePath(@NotNull Path file, boolean isDirectory) {
        return null;
    }

    @NotNull
    @Override
    public FilePath createFilePathOnNonLocal(@NotNull String s, boolean b) {
        return null;
    }

    @NotNull
    @Override
    public FilePath createFilePathOn(@NotNull VirtualFile virtualFile, @NotNull String s) {
        return null;
    }

    @NotNull
    @Override
    public FilePath createFilePath(@NotNull VirtualFile virtualFile, @NotNull String s, boolean b) {
        return null;
    }

    @NotNull
    @Override
    public LocalChangeList createLocalChangeList(@NotNull Project project, @NotNull String s) {
        return null;
    }

    @NotNull
    @Override
    public FilePath createFilePath(@NotNull String s, boolean b) {
        return null;
    }
}

package at.dotti.intellij.plugins.jazz.vcs;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandListener;
import com.intellij.openapi.vfs.LocalFileOperationsHandler;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ThrowableConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class JazzFileSystemListener implements LocalFileOperationsHandler, Disposable, CommandListener {

    private final LocalFileSystem myLfs;

    public JazzFileSystemListener() {
        myLfs = LocalFileSystem.getInstance();
        myLfs.registerAuxiliaryFileOperationsHandler(this);
        ApplicationManager.getApplication().getMessageBus().connect(this).subscribe(CommandListener.TOPIC, this);
    }

    @Override
    public boolean delete(@NotNull VirtualFile virtualFile) throws IOException {
        return false;
    }

    @Override
    public boolean move(@NotNull VirtualFile virtualFile, @NotNull VirtualFile virtualFile1) throws IOException {
        return false;
    }

    @Nullable
    @Override
    public File copy(@NotNull VirtualFile virtualFile, @NotNull VirtualFile virtualFile1, @NotNull String s) throws IOException {
        return null;
    }

    @Override
    public boolean rename(@NotNull VirtualFile virtualFile, @NotNull String s) throws IOException {
        return false;
    }

    @Override
    public boolean createFile(@NotNull VirtualFile virtualFile, @NotNull String s) throws IOException {
        return false;
    }

    @Override
    public boolean createDirectory(@NotNull VirtualFile virtualFile, @NotNull String s) throws IOException {
        return false;
    }

    @Override
    public void afterDone(@NotNull ThrowableConsumer<LocalFileOperationsHandler, IOException> throwableConsumer) {

    }

    @Override
    public void dispose() {

    }
}

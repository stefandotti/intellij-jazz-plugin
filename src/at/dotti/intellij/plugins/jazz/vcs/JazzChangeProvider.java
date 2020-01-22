package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.beans.JazzChange;
import at.dotti.intellij.plugins.jazz.beans.JazzStatus;
import at.dotti.intellij.plugins.jazz.beans.JazzStatusObject;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vcs.LocalFilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.*;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JazzChangeProvider implements ChangeProvider {
    @Override
    public void getChanges(@NotNull VcsDirtyScope vcsDirtyScope, @NotNull ChangelistBuilder changelistBuilder, @NotNull ProgressIndicator progressIndicator, @NotNull ChangeListManagerGate changeListManagerGate) throws VcsException {
        if (vcsDirtyScope.wasEveryThingDirty()) {
            try {
                for (FilePath dir : vcsDirtyScope.getRecursivelyDirtyDirectories()) {
                    JazzStatusObject status = JazzService.getInstance().status(dir);
                    List<JazzChange> changes = status.getChanges();
                    for (JazzChange change : changes) {
                        String path = dir.getPath() + change.getPath();
                        LocalFilePath fp = new LocalFilePath(path, new File(path).isDirectory());
                        updateStatus(fp, vcsDirtyScope.getProject(), changelistBuilder, status);
                    }
                }
            } catch (JazzServiceException e) {
                e.printStackTrace();
            }
        }
        vcsDirtyScope.getDirtyFiles().stream()
                .filter(filePath -> !filePath.getPath().contains(".jazz"))
                .forEach(mark(vcsDirtyScope.getProject(), changelistBuilder));
    }

    private void updateStatus(FilePath filePath, Project project, ChangelistBuilder changelistBuilder, JazzStatusObject status) {
        String relPath = filePath.getPath().substring(project.getBasePath().length());
        status.getChanges().stream()
                .filter(change -> change.getPath().equals(relPath))
                .forEach(change -> processChange(filePath, project, changelistBuilder, change));
    }

    private void processChange(FilePath filePath, Project project, ChangelistBuilder changelistBuilder, JazzChange change) {
        switch (change.getState().getState()) {
            case ADD:
                changelistBuilder.processChangeInList(createChange(null, getRevision(change, filePath), change, FileStatus.ADDED), getChangelist(change), JazzVcs.getKey());
                break;
            case MOVE:
                changelistBuilder.processChangeInList(createChange(getRevision(change, filePath), getRevision(change, filePath), change, FileStatus.SWITCHED), getChangelist(change), JazzVcs.getKey());
                break;
            case DELETE:
                changelistBuilder.processChangeInList(createChange(getRevision(change, filePath), null, change, FileStatus.DELETED), getChangelist(change), JazzVcs.getKey());
                break;
            case CONTENT_CHANGE:
                changelistBuilder.processChangeInList(createChange(getRevision(change, filePath), getRevision(filePath), change, FileStatus.MODIFIED), getChangelist(change), JazzVcs.getKey());
                break;
            case PROPERTY_CHANGE:
                changelistBuilder.processChangeInList(createChange(getRevision(change, filePath), getRevision(change, filePath), change, FileStatus.MODIFIED), getChangelist(change), JazzVcs.getKey());
                break;
            default:
                throw new IllegalArgumentException("jazz change state unknown");
        }
    }

    private ContentRevision getRevision(FilePath filePath) {
        return new JazzContentRevision(filePath);
    }

    private ContentRevision getRevision(JazzChange change, FilePath filePath) {
        return new JazzContentRevision(change, filePath);
    }

    private ChangeList getChangelist(JazzChange change) {
        return null;
    }

    private Change createChange(ContentRevision revBefore, ContentRevision revAfter, JazzChange change, FileStatus state) {
        return new Change(revBefore, revAfter, state);
    }

    private Consumer<? super FilePath> mark(Project project, ChangelistBuilder changelistBuilder) {
        return (Consumer<FilePath>) filePath -> {
            try {
                System.out.println("changed: " + filePath);
                JazzStatusObject status = JazzService.getInstance().status(filePath);
                String relPath = filePath.getPath().substring(project.getBasePath().length());
                if (status.getChanges().stream().anyMatch(change -> change.getPath().equals(relPath))) {
                    System.out.println("changed");
                    updateStatus(filePath, project, changelistBuilder, status);
                } else {
                    System.out.println("not changed");
                }
            } catch (JazzServiceException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public boolean isModifiedDocumentTrackingRequired() {
        return false;
    }

    @Override
    public void doCleanup(List<VirtualFile> list) {

    }
}

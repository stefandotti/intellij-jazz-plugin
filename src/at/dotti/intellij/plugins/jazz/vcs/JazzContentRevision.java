package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.beans.JazzChange;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.LocalFilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.ContentRevision;
import com.intellij.openapi.vcs.history.VcsRevisionNumber;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class JazzContentRevision implements ContentRevision {

    private final VcsRevisionNumber revision;
    private final JazzChange change;
    private FilePath filePath;

    public JazzContentRevision(JazzChange change, FilePath filePath) {
        this.change = change;
        this.filePath = filePath;
        this.revision = change.getState().isAdd() ? VcsRevisionNumber.NULL : new VcsRevisionNumber.Long(1);
    }

    public JazzContentRevision(FilePath filePath) {
        this.filePath = filePath;
        this.change = null;
        this.revision = VcsRevisionNumber.NULL;
    }

    @Nullable
    @Override
    public String getContent() throws VcsException {
        try {
            if (this.change == null) {
                return FileUtils.readFileToString(this.filePath.getIOFile(), filePath.getCharset());
            }
            if (this.revision == VcsRevisionNumber.NULL) {
                return "no-content";
            } else {
                return JazzService.getInstance().getContent(this.filePath, this.change);
            }
        } catch (JazzServiceException | IOException e) {
            throw new VcsException(e);
        }
    }

    @NotNull
    @Override
    public FilePath getFile() {
        return this.filePath;
    }

    @NotNull
    @Override
    public VcsRevisionNumber getRevisionNumber() {
        return this.revision;
    }
}

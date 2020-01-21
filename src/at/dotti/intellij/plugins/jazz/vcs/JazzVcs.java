package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import at.dotti.intellij.plugins.jazz.service.JazzServiceExecutor;
import at.dotti.intellij.plugins.jazz.settings.SettingsBean;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandListener;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.*;
import com.intellij.openapi.vcs.changes.ChangeProvider;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.VirtualFileManagerListener;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class JazzVcs extends AbstractVcs {

    private static final String VCS_NAME = "jazz";
    private static final VcsKey key = createKey(VCS_NAME);
    private final MessageBus bus;
    private final VcsShowConfirmationOption myAddConfirmation;
    private final VcsShowConfirmationOption myDeleteConfirmation;
    private final VcsShowSettingOption myCheckoutOptions;

    private final JazzChangeProvider changeProvider;
    private final JazzFileSystemListener jazzFileSystemListener;
    private final ScheduledExecutorService executor;

    public JazzVcs(@NotNull Project project, MessageBus bus) {
        super(project, VCS_NAME);
        this.bus = bus;

        final ProjectLevelVcsManager vcsManager = ProjectLevelVcsManager.getInstance(project);
        myAddConfirmation = vcsManager.getStandardConfirmation(VcsConfiguration.StandardConfirmation.ADD, this);
        myDeleteConfirmation = vcsManager.getStandardConfirmation(VcsConfiguration.StandardConfirmation.REMOVE, this);
        myCheckoutOptions = vcsManager.getStandardOption(VcsConfiguration.StandardOption.CHECKOUT, this);

        changeProvider = new JazzChangeProvider();

        jazzFileSystemListener = new JazzFileSystemListener();

        this.executor = Executors.newScheduledThreadPool(1);
        this.executor.schedule(new Refresh(), 5000, TimeUnit.MILLISECONDS);
    }

    class Refresh implements Runnable {
        @Override
        public void run() {
            try {
                JazzService.getInstance().status(getProject());
            } catch (JazzServiceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void shutdown() throws VcsException {
        if (this.executor != null) {
            this.executor.shutdownNow();
        }
    }

    @Override
    protected void activate() {
        super.activate();
    }

    public static VcsKey getKey() {
        return key;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Jazz";
    }

    @Override
    public Configurable getConfigurable() {
        return null; // TODO was muss das geliefert werden?
    }

    @Nullable
    @Override
    public ChangeProvider getChangeProvider() {
        return this.changeProvider;
    }
}

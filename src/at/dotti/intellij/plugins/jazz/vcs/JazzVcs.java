package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.settings.SettingsForm;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vcs.*;
import com.intellij.openapi.vcs.changes.ChangeProvider;
import com.intellij.openapi.vcs.checkin.CheckinEnvironment;
import com.intellij.openapi.vcs.history.VcsHistoryProvider;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JazzVcs extends AbstractVcs {

    private static final String VCS_NAME = "jazz";
    private static final VcsKey key = createKey(VCS_NAME);
    private final MessageBus bus;
    private final VcsShowConfirmationOption myAddConfirmation;
    private final VcsShowConfirmationOption myDeleteConfirmation;
    private final VcsShowSettingOption myCheckoutOptions;

    private final JazzChangeProvider changeProvider;
    private final JazzFileSystemListener jazzFileSystemListener;
    private JazzCheckinEnvironment checkinEnvironment;
    private JazzHistoryProvider historyProvider;
    private SettingsForm settings;

    public JazzVcs(@NotNull Project project, MessageBus bus) {
        super(project, VCS_NAME);
        this.bus = bus;

        final ProjectLevelVcsManager vcsManager = ProjectLevelVcsManager.getInstance(project);
        myAddConfirmation = vcsManager.getStandardConfirmation(VcsConfiguration.StandardConfirmation.ADD, this);
        myDeleteConfirmation = vcsManager.getStandardConfirmation(VcsConfiguration.StandardConfirmation.REMOVE, this);
        myCheckoutOptions = vcsManager.getStandardOption(VcsConfiguration.StandardOption.CHECKOUT, this);

        changeProvider = new JazzChangeProvider();

        jazzFileSystemListener = new JazzFileSystemListener();
    }

    public static JazzVcs getInstance(@NotNull Project project) {
        return (JazzVcs) ProjectLevelVcsManager.getInstance(project).findVcsByName(VCS_NAME);
    }

    @Override
    protected void shutdown() throws VcsException {
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
        if (this.settings == null) {
            this.settings = new SettingsForm();
        }
        return this.settings;
    }

    @Nullable
    @Override
    public ChangeProvider getChangeProvider() {
        return this.changeProvider;
    }

    @Nullable
    @Override
    public CheckinEnvironment getCheckinEnvironment() {
        if (this.checkinEnvironment == null) {
            this.checkinEnvironment = new JazzCheckinEnvironment(this);
        }
        return this.checkinEnvironment;
    }

    @Nullable
    @Override
    public VcsHistoryProvider getVcsHistoryProvider() {
        if (this.historyProvider == null) {
            this.historyProvider = new JazzHistoryProvider(this);
        }
        return this.historyProvider;
    }
}

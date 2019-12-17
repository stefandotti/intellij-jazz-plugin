package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.settings.SettingsBean;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.VcsKey;
import com.intellij.openapi.vcs.changes.ChangeProvider;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JazzVcs extends AbstractVcs {

    private static final String VCS_NAME = "jazz";
    private static final VcsKey key = createKey(VCS_NAME);
    private final MessageBus bus;

    private JazzChangeProvider changeProvider = new JazzChangeProvider();

    public JazzVcs(@NotNull Project project, MessageBus bus) {
        super(project, VCS_NAME);
        this.bus = bus;
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

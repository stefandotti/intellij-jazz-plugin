package at.dotti.intellij.plugins.jazz.vcs;

import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CheckoutProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JazzCheckoutProvider implements CheckoutProvider {
    @Override
    public void doCheckout(@NotNull Project project, @Nullable Listener listener) {
        JazzService.getInstance().checkout(project, listener);
    }

    @Override
    public String getVcsName() {
        return "jazz";
    }
}

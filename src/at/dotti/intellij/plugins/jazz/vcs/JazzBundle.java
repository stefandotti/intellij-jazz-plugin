package at.dotti.intellij.plugins.jazz.vcs;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.function.Supplier;

public final class JazzBundle extends DynamicBundle {
    @NonNls
    public static final String BUNDLE = "messages.JazzBundle";

    private static final JazzBundle INSTANCE = new JazzBundle();

    private JazzBundle() {
        super(BUNDLE);
    }

    @NotNull
    public static @Nls
    String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }

    @NotNull
    public static Supplier<String> messagePointer(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        return INSTANCE.getLazyMessage(key, params);
    }

}
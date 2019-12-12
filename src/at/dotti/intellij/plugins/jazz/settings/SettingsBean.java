package at.dotti.intellij.plugins.jazz.settings;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "jazz", storages = {@Storage("jazz.xml")})
public class SettingsBean implements PersistentStateComponent<SettingsBean> {

    private String url;
    private String username;
    private String projectArea;
    private String LScmPath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectArea() {
        return projectArea;
    }

    public void setProjectArea(String projectArea) {
        this.projectArea = projectArea;
    }

    @Nullable
    @Override
    public SettingsBean getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SettingsBean state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getPassword() {
        if (url != null) {
            CredentialAttributes credentialAttributes = createCredentialAttributes(url);
            return PasswordSafe.getInstance().getPassword(credentialAttributes);
        }
        return null;
    }

    public void storePassword(char[] password) {
        if (url != null && username != null && password != null) {
            CredentialAttributes credentialAttributes = createCredentialAttributes(url);
            Credentials credentials = new Credentials(username, password);
            PasswordSafe.getInstance().set(credentialAttributes, credentials);
        }
    }

    private CredentialAttributes createCredentialAttributes(String key) {
        return new CredentialAttributes(CredentialAttributesKt.generateServiceName("XMPP", key));
    }

    public String getLScmPath() {
        return LScmPath;
    }

    public void setLScmPath(String lScmPath) {
        this.LScmPath = lScmPath;
    }
}

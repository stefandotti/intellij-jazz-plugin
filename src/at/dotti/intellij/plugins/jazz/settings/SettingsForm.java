package at.dotti.intellij.plugins.jazz.settings;

import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.file.InvalidPathException;

public class SettingsForm implements Configurable {
    private JTextField url;
    private JTextField username;
    private JPasswordField password;
    private JTextField projectArea;
    private JPanel panel;
    private JButton testConnectionButton;
    private JTextField lscm;
    private JTextPane messages;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Jazz";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        SettingsBean bean = ServiceManager.getService(SettingsBean.class);
        url.setText(bean.getUrl());
        username.setText(bean.getUsername());
        password.setText(bean.getPassword());
        projectArea.setText(bean.getProjectArea());
        lscm.setText(bean.getLScmPath());
        testConnectionButton.addActionListener(event -> test());
        return panel;
    }

    private void test() {
        try {
            messages.setText("testing...");
            JazzService.testConnection(lscm.getText(), url.getText(), username.getText(), password.getPassword());
            messages.setText("connection successfully tested!");
        } catch(InvalidPathException | JazzServiceException e){
            messages.setText(e.getMessage());
        }
    }

    @Override
    public boolean isModified() {
        SettingsBean bean = ServiceManager.getService(SettingsBean.class);
        if (!url.getText().equals(bean.getUrl())) {
            return true;
        } else if (!username.getText().equals(bean.getUsername())) {
            return true;
        } else if (!new String(password.getPassword()).equals(bean.getPassword())) {
            return true;
        } else if (!projectArea.getText().equals(bean.getProjectArea())) {
            return true;
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        SettingsBean bean = ServiceManager.getService(SettingsBean.class);
        bean.setLScmPath(lscm.getText());
        bean.setUsername(username.getText());
        bean.setUrl(url.getText());
        bean.setProjectArea(projectArea.getText());
        bean.storePassword(password.getPassword());
    }
}

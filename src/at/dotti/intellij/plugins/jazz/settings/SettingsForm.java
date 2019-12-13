package at.dotti.intellij.plugins.jazz.settings;

import at.dotti.intellij.plugins.jazz.beans.JazzProjectArea;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import at.dotti.intellij.plugins.jazz.service.JazzServiceExecutor;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.file.InvalidPathException;
import java.util.List;

public class SettingsForm implements Configurable {
    private JTextField url;
    private JTextField username;
    private JPasswordField password;
    private JTextField projectArea;
    private JPanel panel;
    private JButton testConnectionButton;
    private JTextField lscm;
    private JTextPane messages;
    private JButton browseButton;

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
        browseButton.addActionListener(event -> browse());
        return panel;
    }

    private void browse() {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            try {
                updateMessage("listing project areas...");
                List<JazzProjectArea> projectAreas = JazzService.getInstance().listProjectAreas();
                updateMessage("project areas!");
            } catch (InvalidPathException | JazzServiceException e) {
                updateMessage(e.getMessage(), true);
            }
        });
    }

    private void test() {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            try {
                updateMessage("testing...");
                String msg = JazzService.testConnection(lscm.getText(), url.getText(), username.getText(), password.getPassword());
                updateMessage(msg);
            } catch (InvalidPathException | JazzServiceException e) {
                updateMessage(e.getMessage(), true);
            }
        });
    }

    private void updateMessage(String msg) {
        updateMessage(msg, false);
    }

    private void updateMessage(String msg, boolean error) {
        messages.setText(msg);
        if (error) {
            messages.setBackground(JBColor.MAGENTA);
            messages.setForeground(JBColor.foreground());
        } else {
            messages.setBackground(JBColor.background());
            messages.setForeground(JBColor.foreground());
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
        } else if (!lscm.getText().equals(bean.getLScmPath())) {
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

package at.dotti.intellij.plugins.jazz.settings;

import at.dotti.intellij.plugins.jazz.beans.JazzProjectArea;
import at.dotti.intellij.plugins.jazz.beans.JazzProjectAreaList;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.file.InvalidPathException;

public class SettingsForm implements Configurable {
    private JTextField url;
    private JTextField username;
    private JPasswordField password;
    private JComboBox<String> projectArea;
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
        projectArea.setSelectedItem(bean.getProjectArea());
        lscm.setText(bean.getLScmPath());
        testConnectionButton.addActionListener(event -> test());
        browseButton.addActionListener(event -> browse());
        return panel;
    }

    private void browse() {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            try {
                updateMessage("listing project areas...");
                JazzProjectAreaList projectAreas = JazzService.getInstance().listProjectAreas();
                projectArea.removeAllItems();
                projectAreas.getProjectAreas().stream().
                        map(JazzProjectArea::getName).
                        forEach(i->projectArea.addItem(i));
                updateMessage("project areas fetched!");
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
            messages.setBackground(panel.getBackground());
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
        } else if (projectArea.getSelectedItem() != null && !projectArea.getSelectedItem().equals(bean.getProjectArea())) {
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
        bean.setProjectArea(projectArea.getSelectedItem().toString());
        bean.storePassword(password.getPassword());
    }
}

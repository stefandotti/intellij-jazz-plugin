package at.dotti.intellij.plugins.jazz.project;

import at.dotti.intellij.plugins.jazz.JazzPlugin;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;

public class JazzProjectImpl implements JazzProject {

    @Override
    public void projectOpened() {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            try {
                JazzService.getInstance().initialize();
            } catch (JazzServiceException e) {
                Notifications.Bus.notify(new Notification(JazzPlugin.GROUP_ID, "Error", e.getMessage(), NotificationType.ERROR));
            }
        });
    }

    @Override
    public void projectClosed() {
        JazzService.getInstance().shutdown();
    }

}

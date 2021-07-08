// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package at.dotti.intellij.plugins.jazz.dialogs;

import at.dotti.intellij.plugins.jazz.beans.JazzStatusObject;
import at.dotti.intellij.plugins.jazz.exceptions.JazzServiceException;
import at.dotti.intellij.plugins.jazz.service.JazzService;
import at.dotti.intellij.plugins.jazz.vcs.JazzVcs;
import at.dotti.intellij.plugins.jazz.vcs.JazzWorkspaceContent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.NotificationsManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.util.Consumer;
import com.intellij.util.concurrency.annotations.RequiresEdt;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static at.dotti.intellij.plugins.jazz.vcs.JazzBundle.message;
import static com.intellij.notification.NotificationAction.createSimpleExpiring;
import static com.intellij.openapi.application.ApplicationManager.getApplication;
import static com.intellij.ui.ScrollPaneFactory.createScrollPane;
import static com.intellij.util.ui.JBUI.Borders.empty;

public class WorkspacePanel extends SimpleToolWindowPanel {

    private static final Logger LOG = Logger.getInstance(WorkspacePanel.class);

    private static final String TOOLBAR_GROUP = "Jazz.WorkspaceView.Toolbar";
    private static final String TOOLBAR_PLACE = "Jazz.WorkspaceView";
    private static final String POPUP_GROUP = "Jazz.WorkspaceView.Popup";

    private final Project myProject;
    private final JPanel myPanel = new JBPanel<>(new VerticalLayout(8));
    private boolean isRefreshing;

    private static final @NonNls
    String HELP_ID = "reference.vcs.jazz.working.copies.information";

    private JazzStatusObject jazzStatus;

    public WorkspacePanel(@NotNull Project project) {
        super(false, true);
        myProject = project;
        myProject.getMessageBus().connect().subscribe(JazzVcs.ROOTS_RELOADED, (Consumer<Boolean>) this::rootsReloaded);

        myPanel.setBorder(empty(2, 4));
        setContent(createScrollPane(myPanel));

        ActionGroup toolbarGroup = (ActionGroup) ActionManager.getInstance().getAction(TOOLBAR_GROUP);
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(TOOLBAR_PLACE, toolbarGroup, false);
        setToolbar(toolbar.getComponent());

        rootsReloaded(true);
        refresh();
    }

    @RequiresEdt
    public boolean isRefreshing() {
        return isRefreshing;
    }

    @RequiresEdt
    public void refresh() {
        if (isRefreshing) return;

        isRefreshing = true;
        doRefresh();
    }

    @NotNull
    private JazzVcs getVcs() {
        return JazzVcs.getInstance(myProject);
    }

    @Override
    public @Nullable
    Object getData(@NotNull String dataId) {
        if (PlatformDataKeys.HELP_ID.is(dataId)) return HELP_ID;
        return super.getData(dataId);
    }

    private void doRefresh() {
        getApplication().invokeLater(() -> setWorkspace(), ModalityState.NON_MODAL);
    }

    @RequiresEdt
    private void setWorkspace() {
        isRefreshing = false;
        try {
            jazzStatus = JazzService.getInstance().status(this.myProject);
        } catch (JazzServiceException e) {
            LOG.error(e);
            return;
        }
        updateList();
    }

    private void rootsReloaded(boolean rootsChanged) {
        if (rootsChanged) {
            if (getApplication().isUnitTestMode()) {
                doRefresh();
            } else {
                getApplication().executeOnPooledThread(this::doRefresh);
            }
        } else {
            getApplication().invokeLater(() -> isRefreshing = false, ModalityState.NON_MODAL);
        }
    }

    private void updateList() {
        myPanel.removeAll();

        WorkspaceInfoPanel infoPanel = new WorkspaceInfoPanel(this.jazzStatus);
        infoPanel.setFocusable(true);
        infoPanel.setBorder(null);

        ActionGroup group = (ActionGroup) ActionManager.getInstance().getAction("Jazz.WorkspaceView.Popup");
        infoPanel.setComponentPopupMenu(ActionManager.getInstance().createActionPopupMenu(POPUP_GROUP, group).getComponent());

        myPanel.add(infoPanel);

        myPanel.revalidate();
        myPanel.repaint();
    }

    private void showErrorNotification(boolean hasErrors) {
        NotificationsManager manager = NotificationsManager.getNotificationsManager();
        ErrorsFoundNotification[] notifications = manager.getNotificationsOfType(ErrorsFoundNotification.class, myProject);

        if (hasErrors) {
            // do not show notification if it is already shown
            if (notifications.length == 0) {
                new ErrorsFoundNotification(myProject).notify(myProject.isDefault() ? null : myProject);
            }
        } else {
            // expire notification
            for (ErrorsFoundNotification notification : notifications) {
                notification.expire();
            }
        }
    }

    private static final class ErrorsFoundNotification extends Notification {
        private ErrorsFoundNotification(@NotNull final Project project) {
            super("Jazz Notification Group", "", message("jazz.roots.detection.errors.found.description"), NotificationType.ERROR);

            addAction(createSimpleExpiring(message("jazz.roots.detection.errors.found.action.text"), () -> JazzWorkspaceContent.show(project)));
        }
    }
}

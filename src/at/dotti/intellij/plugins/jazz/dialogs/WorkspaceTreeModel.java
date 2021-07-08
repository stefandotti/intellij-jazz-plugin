package at.dotti.intellij.plugins.jazz.dialogs;

import at.dotti.intellij.plugins.jazz.beans.JazzStatusObject;

import javax.swing.tree.DefaultTreeModel;

public class WorkspaceTreeModel extends DefaultTreeModel {

    public WorkspaceTreeModel(JazzStatusObject status) {
        super(new WorkspaceTreeNode(status));
    }

}

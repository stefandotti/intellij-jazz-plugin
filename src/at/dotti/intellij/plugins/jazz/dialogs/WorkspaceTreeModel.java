package at.dotti.intellij.plugins.jazz.dialogs;

import at.dotti.intellij.plugins.jazz.beans.JazzStatusObject;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class WorkspaceTreeModel extends DefaultTreeModel {

    public WorkspaceTreeModel() {
        super(new WorkspaceTreeNode());
    }

    public void setStatus(JazzStatusObject status) {
        ((WorkspaceTreeNode)this.getRoot()).setStatus(status);
        this.nodeStructureChanged((TreeNode) this.getRoot());
    }

}

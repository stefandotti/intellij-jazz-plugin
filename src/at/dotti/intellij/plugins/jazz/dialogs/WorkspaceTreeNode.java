package at.dotti.intellij.plugins.jazz.dialogs;

import at.dotti.intellij.plugins.jazz.beans.*;
import at.dotti.intellij.plugins.jazz.vcs.JazzBundle;

import javax.swing.tree.DefaultMutableTreeNode;

public class WorkspaceTreeNode extends DefaultMutableTreeNode {

    public WorkspaceTreeNode() {
        super(JazzBundle.message("jazz.status.loading"));
    }

    public void setStatus(JazzStatusObject status) {
        this.removeAllChildren();
        this.setUserObject(JazzBundle.message("jazz.status.loading"));
        for (JazzWorkspace workspace : status.getWorkspaces()) {
            this.add(new WorkspaceTreeNode(workspace));
        }
        this.setUserObject(null);
    }

    public WorkspaceTreeNode(JazzWorkspace workspace) {
        super(workspace.getName());
        for (JazzComponent component : workspace.getComponents()) {
            this.add(new WorkspaceTreeNode(component));
        }
    }

    public WorkspaceTreeNode(JazzComponent component) {
        super(component.getName());
        for (JazzIncomingChange change : component.getIncomingChanges()) {
            this.add(new WorkspaceTreeNode(change));
        }
        for (JazzOutgoingChange change : component.getOutgoingChanges()) {
            this.add(new WorkspaceTreeNode(change));
        }
    }

    public WorkspaceTreeNode(JazzIncomingChange change) {
        super(JazzBundle.message("jazz.incoming") + " [" + change.getComment() + "]");
        for (JazzChange changeChange : change.getChanges()) {
            this.add(new WorkspaceTreeNode(changeChange));
        }
    }

    public WorkspaceTreeNode(JazzChange changeChange) {
        super(changeChange.getPath());
    }

    public WorkspaceTreeNode(JazzOutgoingChange change) {
        super(JazzBundle.message("jazz.outgoing") + " [" + change.getComment() + "]");
        for (JazzChange changeChange : change.getChanges()) {
            this.add(new WorkspaceTreeNode(changeChange));
        }
    }
}

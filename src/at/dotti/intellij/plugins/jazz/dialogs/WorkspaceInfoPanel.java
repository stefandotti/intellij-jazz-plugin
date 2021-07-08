package at.dotti.intellij.plugins.jazz.dialogs;

import at.dotti.intellij.plugins.jazz.beans.JazzStatusObject;
import com.intellij.ui.treeStructure.Tree;

public class WorkspaceInfoPanel extends Tree {

    public WorkspaceInfoPanel(JazzStatusObject status) {
        setModel(new WorkspaceTreeModel(status));
        setShowsRootHandles(false);
    }

}

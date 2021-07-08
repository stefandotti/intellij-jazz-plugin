package at.dotti.intellij.plugins.jazz.dialogs;

import com.intellij.ui.treeStructure.Tree;

public class WorkspaceInfoPanel extends Tree {

    public WorkspaceInfoPanel() {
        setModel(new WorkspaceTreeModel());
        setShowsRootHandles(false);
    }

}

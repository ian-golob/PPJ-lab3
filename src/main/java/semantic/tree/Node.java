package semantic.tree;

import java.util.ArrayList;
import java.util.List;

public class Node extends TreeElement{
    private List<TreeElement> children = new ArrayList<>();

    public List<TreeElement> getChildren() {
        return children;
    }

    public Node(Node parent, String name) {
        super(parent, name);
        isLeaf = false;
    }
}

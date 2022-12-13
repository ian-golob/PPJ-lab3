package semantic.tree;

public abstract class TreeElement {
    protected Node parent;
    protected String name;
    protected boolean isLeaf;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public TreeElement(Node parent, String name) {
        this.parent = parent;
        this.name = name;
    }
}

package semantic.model.util;

import semantic.tree.Leaf;
import semantic.tree.Node;

import static semantic.model.util.ConstantUtil.getCharArraySize;

public class TreeUtil {
    public static int charArraySize(Node node){
        if (node.getChildren().size() != 1) return 0;
        if (node.getChild(0) instanceof Leaf){
            if (node.getChild(0).getName().equals("NIZ_ZNAKOVA")) return getCharArraySize(((Leaf) node.getChild(0)).getSourceText());
            else return 0;
        }
        else return charArraySize((Node) node.getChild(0));
    }
}

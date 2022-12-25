import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class SemantickiAnalizator {


    private final ScopeController scopeController;

    private final ProductionChecker checker;

    private final InputStream in;

    private final PrintStream out;

    public SemantickiAnalizator(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;

        scopeController = new ScopeController();
        checker = new ProductionChecker(scopeController, out);
    }

    public static void main(String[] args) {
        SemantickiAnalizator sa = new SemantickiAnalizator(System.in, System.out);

        sa.analyzeInput();
    }


    private int calcDepth(String s){
        int i;
        for (i = 0; s.charAt(i) == ' '; i++);
        return i;
    }

    private Leaf parseLeaf(String s){
        int firstSpace = s.indexOf(' ');
        int secondSpace = s.substring(firstSpace + 1).indexOf(' ') + firstSpace + 1;
        return new Leaf(null, s.substring(0, firstSpace), Integer.parseInt(s.substring(firstSpace + 1, secondSpace)), s.substring(secondSpace + 1));
    }

    /*
    private void printTree(PrintStream out, TreeElement node){
        out.println(node.getName());
        if (!node.isLeaf()){
            out.println("Children:");
            for (var child : ((Node) node).getChildren()){
                out.println(child.getName());
            }
            out.println();
            for (var child : ((Node) node).getChildren()){
                printTree(out,child);
            }
        }
    }
     */

    private Node parseInput(InputStream in){
        List<String> inputLines = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        Node root = new Node(null, inputLines.get(0));
        Node currentNode = root;
        int oldDepth = 0;
        int depth;
        String currentLine;
        for (int i = 1; i < inputLines.size(); i++){
            currentLine = inputLines.get(i);
            depth = calcDepth(currentLine);
            currentLine = currentLine.substring(depth);
            boolean isNode = currentLine.charAt(0) == '<';
            TreeElement newTreeElement;

            if (!isNode){
                newTreeElement = parseLeaf(currentLine);
            } else{
                newTreeElement = new Node(null, currentLine);
            }

            for (int j = 0; j < oldDepth - depth; j++) currentNode = currentNode.getParent();

            newTreeElement.setParent(currentNode);
            currentNode.getChildren().add(newTreeElement);
            if (isNode) currentNode = (Node) newTreeElement;
            oldDepth = depth;
        }

        return root;
    }

    public void analyzeInput() {

        Node root = parseInput(in);
        //printTree(out, root);

        checker.checkRoot(root);
    }


}

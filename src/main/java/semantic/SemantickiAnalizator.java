package semantic;

import semantic.checker.ProductionChecker;
import semantic.scope.ScopeController;
import semantic.tree.Node;

import java.io.InputStream;
import java.io.PrintStream;

public class SemantickiAnalizator {


    private final ScopeController scopeController;

    private final ProductionChecker checker;

    public static void main(String[] args) {
        SemantickiAnalizator sa = new SemantickiAnalizator();

        sa.analyzeInput(System.in, System.out, System.err);
    }

    public SemantickiAnalizator(){
        scopeController = new ScopeController();
        checker = new ProductionChecker(scopeController, System.out);
    }

    public void analyzeInput(InputStream in, PrintStream out, PrintStream err) {

        Node root = null;
        //root = parseInput(input);

        checker.check(root);
    }
}

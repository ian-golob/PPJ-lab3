package semantic;

import semantic.checker.ProductionChecker;
import semantic.scope.ScopeController;

import java.io.InputStream;
import java.io.PrintStream;

public class SemantickiAnalizator {


    private final ScopeController scopeController;

    private final ProductionChecker checker;

    public SemantickiAnalizator(){
        scopeController = new ScopeController();
        checker = new ProductionChecker(scopeController);

    }

    public void analyzeInput(InputStream input, PrintStream output) {

        //parseInput(input)

        //checkTree(output)

    }
}

package semantic.checker;

import semantic.SemanticException;
import semantic.scope.ScopeController;
import semantic.tree.Node;

@FunctionalInterface
public interface Rule {

    boolean check(Node node, ProductionChecker checker, ScopeController scope) throws SemanticException;

}

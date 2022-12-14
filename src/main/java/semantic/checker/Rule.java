package semantic.checker;

import semantic.SemanticException;
import semantic.scope.ScopeController;
import semantic.tree.Node;

@FunctionalInterface
public interface Rule {

    void check(Node node, ProductionChecker checker, ScopeController scope) throws SemanticException;

}

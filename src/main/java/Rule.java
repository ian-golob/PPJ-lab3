@FunctionalInterface
public interface Rule {

    void check(Node node, ProductionChecker checker, ScopeController scope) throws SemanticException;

}

public interface VariableScope {

    VariableScope getOuterScope();

    void defineNewVariable(Variable variable) throws SemanticException;

    void declareNewFunction(Function function) throws SemanticException;

    Variable getVariable(String variableName) throws SemanticException;

    Function getFunction(String functionName) throws SemanticException;

    boolean isDeclared(String name);

    ScopeElement get(String name);

    boolean functionIsDeclaredLocally(String functionName);

    boolean isDeclaredGlobally(String functionName);

    Function getGloballyDeclaredFunction(String functionName) throws SemanticException;
}

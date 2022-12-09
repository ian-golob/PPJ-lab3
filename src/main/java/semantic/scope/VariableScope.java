package semantic.scope;

import semantic.SemanticException;

public interface VariableScope {

    VariableScope getOuterScope();

    void defineNewVariable(Variable variable) throws SemanticException;

    Variable getVariable(String variableName);
}

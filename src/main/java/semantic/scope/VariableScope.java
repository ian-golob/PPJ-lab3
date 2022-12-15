package semantic.scope;

import semantic.SemanticException;
import semantic.model.variable.Variable;

public interface VariableScope {

    VariableScope getOuterScope();

    void defineNewVariable(Variable variable) throws SemanticException;

    Variable getVariable(String variableName);

}

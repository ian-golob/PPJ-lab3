package semantic.scope;

import semantic.SemanticException;
import semantic.scope.variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariableScope implements VariableScope {


    private final Map<String, Variable> variableMap = new HashMap<>();

    @Override
    public VariableScope getOuterScope() {
        throw new UnsupportedOperationException("The global scope has no outer scope.");
    }

    @Override
    public void defineNewVariable(Variable variable) throws SemanticException {
        if(variableMap.containsKey(variable.getName())){
            throw new SemanticException("Variable already defined in this scope");
        }

        variableMap.put(variable.getName(), variable);
    }

    @Override
    public Variable getVariable(String variableName) {
        return variableMap.get(variableName);
    }
}

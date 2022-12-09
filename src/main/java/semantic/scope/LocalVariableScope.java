package semantic.scope;

import semantic.SemanticException;

import java.util.HashMap;
import java.util.Map;

public class LocalVariableScope implements VariableScope {

    private final Map<String, Variable> variableMap = new HashMap<>();

    private final VariableScope outerScope;

    public LocalVariableScope(VariableScope outerScope) {
        this.outerScope = outerScope;
    }

    @Override
    public VariableScope getOuterScope() {
        return outerScope;
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
        if(variableMap.containsKey(variableName)){
            return variableMap.get(variableName);
        }

        return outerScope.getVariable(variableName);
    }
}

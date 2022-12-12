package semantic.scope;

import semantic.SemanticException;
import semantic.scope.function.Function;
import semantic.scope.variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class ScopeController {

    private VariableScope currentVariableScope;

    private final Map<String, Function> definedFunctions = new HashMap<>();

    private final Map<String, Function> declaredFunctions = new HashMap<>();

    public ScopeController(){
        currentVariableScope = new GlobalVariableScope();
    }

    public void defineNewScope(){
        currentVariableScope = new LocalVariableScope(currentVariableScope);
    }

    public void exitLastScope(){
        currentVariableScope = currentVariableScope.getOuterScope();
    }

    public void declareVariable(Variable variable) throws SemanticException {
        currentVariableScope.defineNewVariable(variable);
    }

    public Variable getVariable(String variableName) {
        return currentVariableScope.getVariable(variableName);
    }

    public boolean variableIsDeclared(String variableName){
        Variable variable = getVariable(variableName);

        return variable != null;
    }

    public void declareFunction(Function function) throws SemanticException {
        if(declaredFunctions.containsKey(function.getName())){
            throw new SemanticException("Function already declared.");
        }

        declaredFunctions.put(function.getName(), function);
    }

    public void defineFunction(Function function) throws SemanticException {
        if(definedFunctions.containsKey(function.getName())){
            throw new SemanticException("Function already defined.");
        }

        if(declaredFunctions.containsKey(function.getName())){
            Function declaredFunction = declaredFunctions.get(function.getName());

            if(!declaredFunction.matchesSignatureOf(function)){
                throw new SemanticException("Function declaration and definition signatures do not match.");
            }
        }

        declaredFunctions.put(function.getName(), function);
        definedFunctions.put(function.getName(), function);
    }

    public void getFunction(String functionName) throws SemanticException {
        definedFunctions.get(functionName);
    }

}

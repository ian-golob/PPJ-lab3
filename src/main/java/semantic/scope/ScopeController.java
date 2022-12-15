package semantic.scope;

import semantic.SemanticException;
import semantic.model.function.Function;
import semantic.model.variable.Variable;

import java.util.HashMap;
import java.util.Map;

public class ScopeController {

    private VariableScope currentVariableScope;

    private final Map<String, Function> definedFunctions = new HashMap<>();

    private final Map<String, Function> declaredFunctions = new HashMap<>();

    private Function currentFunction;

    private int loopCounter = 0;

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

    public void requireDeclaredVariable(String variableName) throws SemanticException{
        if(!variableIsDeclared(variableName)){
            throw new SemanticException("Variable not declared");
        }
    }

    public void declareFunction(Function function) throws SemanticException {
        if(declaredFunctions.containsKey(function.getName())){
            throw new SemanticException("Function already declared.");
        }

        declaredFunctions.put(function.getName(), function);
    }

    public void startFunctionDeclaration(Function function) throws SemanticException {
        declareFunction(function);

        currentFunction = function;
    }

    public void endFunctionDeclaration() {
        currentFunction = null;
    }

    public Function getCurrentFunction() {
        return currentFunction;
    }

    public void enterLoop(){
        loopCounter++;
    }

    public void exitLoop(){
        loopCounter--;
    }

    public boolean inLoop(){
        return loopCounter > 0;
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

    public Function getDefinedFunction(String functionName) {
        return definedFunctions.get(functionName);
    }

    public Function getDeclaredFunction(String functionName) {
        return definedFunctions.get(functionName);
    }

}

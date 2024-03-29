package semantic.model.function;

import semantic.SemanticException;
import semantic.model.type.DataType;
import semantic.model.type.FunctionType;
import semantic.scope.ScopeElement;

import java.util.ArrayList;
import java.util.List;

public class Function implements ScopeElement {

    private final String name;

    private final DataType returnType;

    private final List<DataType> parameters;

    private final FunctionType functionType;

    private boolean isDefined = false;

    private final boolean voidParameters;

    public Function(String name, FunctionType functionType) throws SemanticException {

        this.functionType = functionType;

        DataType returnType = functionType.getReturnType();
        List<DataType> parameters = functionType.getParameters();

        this.name = name;
        this.returnType = returnType;
        this.parameters = new ArrayList<>();

        if(parameters.size() == 0 ||
                parameters.size() == 1 && parameters.get(0) == DataType.VOID){
            voidParameters = true;

        } else {
            voidParameters = false;

            for(DataType parameter: parameters){
                if(parameter == DataType.VOID){
                    throw new SemanticException();
                }

                this.parameters.add(parameter);
            }
        }
    }

    public String getName() {
        return name;
    }

    public DataType getReturnType() {
        return returnType;
    }

    public FunctionType getFunctionType() {
        return functionType;
    }

    public List<DataType> getParameters() {
        return parameters;
    }

    public boolean isVoidParameters() {
        return voidParameters;
    }

    public boolean matchesSignatureOf(Function other){

        // check return type
        if(this.returnType != other.returnType){
            return false;
        }

        // check if parameters are void
        if(this.voidParameters && !other.voidParameters ||
                !this.voidParameters && other.voidParameters){
            return false;
        }

        // check parameters length
        if(this.parameters.size() != other.parameters.size()){
            return false;
        }

        // check parameters
        for(int i = 0; i < parameters.size(); i++){
            if(this.parameters.get(i) != other.parameters.get(i)){
                return false;
            }
        }

        return true;
    }

    public void define(){
        isDefined = true;
    }

    public boolean isDefined(){
        return isDefined;
    }

    @Override
    public DataType getType() {
        return functionType;
    }

    @Override
    public Boolean isLValue() {
        return false;
    }
}

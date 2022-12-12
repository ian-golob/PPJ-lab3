package semantic.scope.function;

import semantic.SemanticException;
import semantic.scope.type.DataType;

import java.util.ArrayList;
import java.util.List;

public class Function {

    private final String name;

    private final DataType returnType;

    private final List<DataType> parameters;

    private final boolean voidParameters;

    public Function(String name) throws SemanticException {
        this(name, DataType.VOID, DataType.VOID);
    }

    public Function(String name, DataType returnType) throws SemanticException {
        this(name, returnType, DataType.VOID);
    }

    public Function(String name, DataType returnType, DataType... parameters) throws SemanticException {
        this.name = name;
        this.returnType = returnType;
        this.parameters = new ArrayList<>();

        if(parameters.length == 0 ||
                parameters.length == 1 && parameters[0] == DataType.VOID){
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

    public List<DataType> getParameters() {
        return parameters;
    }

    public boolean isVoidParameters() {
        return voidParameters;
    }

    public boolean matchesSignatureOf(Function other){

        // check function name
        if(!this.name.equals(other.name)){
            return false;
        }

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
}

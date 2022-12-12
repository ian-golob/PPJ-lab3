package semantic.scope.variable;

import semantic.SemanticException;
import semantic.scope.type.DataType;

import java.util.Objects;

public class Variable {

    private final String name;

    private final DataType type;

    private final boolean isConst;

    private final boolean isArray;


    public Variable(String name, DataType type, boolean isConst, boolean isArray) throws SemanticException {
        this.name = Objects.requireNonNull(name);

        if(type == DataType.VOID){
            throw new SemanticException();
        }
        this.type = Objects.requireNonNull(type);

        this.isConst = isConst;
        this.isArray = isArray;
    }

    public String getName() {
        return name;
    }

    public DataType getType() {
        return type;
    }

    public boolean isConst() {
        return isConst;
    }

    public boolean isArray() {
        return isArray;
    }
}

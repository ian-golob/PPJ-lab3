package semantic.scope;

import java.util.Objects;

public class Variable {

    private final String name;

    private final VariableType type;

    private final boolean isConst;

    private final boolean isArray;


    public Variable(String name, VariableType type, boolean isConst, boolean isArray) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.isConst = isConst;
        this.isArray = isArray;
    }

    public String getName() {
        return name;
    }

    public VariableType getType() {
        return type;
    }

    public boolean isConst() {
        return isConst;
    }

    public boolean isArray() {
        return isArray;
    }
}

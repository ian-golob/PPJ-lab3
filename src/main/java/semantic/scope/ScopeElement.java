package semantic.scope;

import semantic.model.type.DataType;


public interface ScopeElement {

    DataType getType();

    Boolean isLValue();
}

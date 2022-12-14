package semantic.model.util;

import semantic.SemanticException;

public class ConstantUtil {
    //TODO


    public static void requireIntValue(String value) throws SemanticException {
        try {

            Integer.valueOf(value);

        } catch (IllegalArgumentException ex){
            throw new SemanticException();
        }
    }
}

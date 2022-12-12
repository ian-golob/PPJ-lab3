package semantic.semantic.scope.type;


import org.junit.jupiter.api.Test;
import semantic.SemanticException;
import semantic.model.type.ArrayType;

import static org.junit.jupiter.api.Assertions.*;
import static semantic.model.type.NumericType.*;

public class ArrayTypeTest {

    @Test
    public void implicitlyCastableToTest() throws SemanticException {
        assertTrue(ArrayType.of(INT).implicitlyCastableTo(ArrayType.of(CONST_INT)));
        assertTrue(ArrayType.of(INT).implicitlyCastableTo(ArrayType.of(INT)));
        assertTrue(ArrayType.of(CHAR).implicitlyCastableTo(ArrayType.of(CONST_CHAR)));
        assertTrue(ArrayType.of(CHAR).implicitlyCastableTo(ArrayType.of(CHAR)));
        assertTrue(ArrayType.of(CONST_INT).implicitlyCastableTo(ArrayType.of(CONST_INT)));
        assertTrue(ArrayType.of(CONST_CHAR).implicitlyCastableTo(ArrayType.of(CONST_CHAR)));

        assertFalse(ArrayType.of(CHAR).implicitlyCastableTo(ArrayType.of(INT)));
        assertFalse(ArrayType.of(INT).implicitlyCastableTo(ArrayType.of(CHAR)));

        assertThrows(SemanticException.class, () -> ArrayType.of(VOID));
        assertThrows(SemanticException.class, () -> ArrayType.of(ArrayType.of(INT)));
    }


}

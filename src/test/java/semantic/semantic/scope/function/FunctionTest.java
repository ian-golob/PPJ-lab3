package semantic.semantic.scope.function;

import org.junit.jupiter.api.Test;
import semantic.SemanticException;
import semantic.scope.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static semantic.scope.type.DataType.*;
import static semantic.scope.type.NumericType.*;

public class FunctionTest {


    @Test
    public void matchesSignatureTest() throws SemanticException {
        assertTrue(new Function("ime").matchesSignatureOf(new Function("ime")));
        assertFalse(new Function("ime").matchesSignatureOf(new Function("drugo ime")));

        assertTrue(new Function("ime", VOID).matchesSignatureOf(new Function("ime", VOID)));
        assertTrue(new Function("ime", INT) .matchesSignatureOf(new Function("ime", INT)));
        assertFalse(new Function("ime", INT) .matchesSignatureOf(new Function("ime", VOID)));
        assertFalse(new Function("ime", VOID).matchesSignatureOf(new Function("ime", INT)));

        assertTrue(new Function("ime", INT, CONST_INT) .matchesSignatureOf(new Function("ime", INT, CONST_INT)));
        assertFalse(new Function("ime", INT, CONST_INT) .matchesSignatureOf(new Function("ime", INT, CHAR)));

        assertThrows(SemanticException.class, () -> new Function("ime", VOID, INT, VOID));
    }

}

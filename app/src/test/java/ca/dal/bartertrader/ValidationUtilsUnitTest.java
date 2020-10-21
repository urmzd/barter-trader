package ca.dal.bartertrader;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationUtilsUnitTest {

    @Test
    public void emailIsValid_CorrectEmail_ReturnsTrue() {
        assertTrue(ValidationUtils.emailIsValid("theurmzd@gmail.com"));
    }

    @Test
    public void emailIsValid_InvalidEmail_ReturnsFalse() {
        assertFalse(ValidationUtils.emailIsValid("theurmzd"));
    }
}

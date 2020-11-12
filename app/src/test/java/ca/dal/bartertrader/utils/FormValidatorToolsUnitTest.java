package ca.dal.bartertrader.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FormValidatorToolsUnitTest {

    @Test
    public void doPasswordsMatchReturnFalse() {
        assertThat(FormValidatorTools.doPasswordsMatch("Test", "Tset"), is(false));
    }

    @Test
    public void doPasswordsMatchReturnsTrue() {
        assertThat(FormValidatorTools.doPasswordsMatch("Test", "Test"), is(true));
    }

    @Test
    public void isPasswordStrongReturnsFalse() {
        assertThat(FormValidatorTools.isPasswordStrong("contain"), is(false));
        assertThat(FormValidatorTools.isPasswordStrong("contains1"), is(false));
        assertThat(FormValidatorTools.isPasswordStrong("contains!"), is(false));
        assertThat(FormValidatorTools.isPasswordStrong("containsU"), is(false));
    }

    @Test
    public void isPasswordStrongReturnsTrue() {
        assertThat(FormValidatorTools.isPasswordStrong("YesPasswo1dIsStr00ng!"), is(true));
    }

    @Test
    public void isEmailValidReturnsFalse() {
        assertThat(FormValidatorTools.isEmailValid("user.domain.ca"), is(false));
    }

    @Test
    public void isEmailValidReturnsTrue() {
        assertThat(FormValidatorTools.isEmailValid("user@domain.ca"), is(true));
    }

    @Test
    public void isTextAlphaNumericReturnsTrue() {
        assertThat(FormValidatorTools.isTextAlphaNumeric("1test1"), is(true));
    }

    @Test
    public void isTextAlphaNumericReturnsFalse() {
        assertThat(FormValidatorTools.isTextAlphaNumeric("1test@"), is(false));
    }

    @Test
    public void isTextValidReturnsFalse() {
        assertThat(FormValidatorTools.isTextValid(""), is(false));
    }

    @Test
    public void isTextValidReturnsTrue() {
        assertThat(FormValidatorTools.isTextValid("Test"), is(true));
    }

    @Test
    public void isNameValidReturnsFalse() {
        assertThat(FormValidatorTools.isNameValid("!1293mzd"), is(false));
    }

    @Test
    public void isNameValidReturnsTrue() {
        assertThat(FormValidatorTools.isNameValid("urmzd"), is(true));
        assertThat(FormValidatorTools.isNameValid("M'Donald"), is(true));
        assertThat(FormValidatorTools.isNameValid("John-Doe"), is(true));
    }

}

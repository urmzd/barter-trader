package ca.dal.bartertrader.utils.handler.resource;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class StatusUnitTest {
    @Test
    public void enumsAreDefined() {
        assertThat(Status.FULFILLED, is(notNullValue()));
        assertThat(Status.REJECTED, is(notNullValue()));
        assertThat(Status.PENDING, is(notNullValue()));
    }
}

package ca.dal.bartertrader.utils.handler.resource;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResourceUnitTest {

    private static String data;
    private static Exception exception;

    private static Resource<String> success;
    private static Resource<Exception> rejected;

    @BeforeClass
    public static void onlyOnce() {
        data = "Test";
        exception = new IndexOutOfBoundsException();

        success  = Resource.fulfilled(data);
        rejected = Resource.rejected(exception);
    }

    @Test
    public void getStatusReturnsEnum() {
        assertThat(success.getStatus(), is(Status.FULFILLED));
        assertThat(rejected.getStatus(), is(Status.REJECTED));
    }

    @Test
    public void getDataReturnsContent() {
        assertThat(success.getData(), is(data));
        assertThat(rejected.getData(), is(nullValue()));
    }

    @Test
    public void getErrorReturnsException() {
        assertThat(success.getError(), is(nullValue()));
        assertThat(rejected.getError(), is(exception));
    }

}

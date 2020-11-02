package ca.dal.bartertrader.utils.handler;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EventUnitTest {

    private static String payload;
    private static Event<String> event;

    @BeforeClass
    public static void onlyOnce() {
        payload = "Test";
        event = new Event<>(payload);
    }

    @Test
    public void handleReturnsPayload() {
        assertThat(event.handle(), is(payload));
    }

    @Test
    public void handleSetsPropertyToTrue() {
        event.handle();
        assertThat(event.getHandled(), is(true));
    }

    @Test
    public void handledPeekReturnsPayload() {
        assertThat(event.peek(), is(payload));
    }

    @Test
    public void unhandledPeekReturnsNull() {
        Event<String> newEvent = new Event<>(payload);
        assertThat(newEvent.peek(), is(nullValue()));
    }
}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DequeTest {

    @Test
    public void rejectAddingFirstNull() {
        Deque<Object> deque = new Deque<>();
        Assertions.assertThrows(IllegalArgumentException.class,
                                () -> deque.addFirst(null));
    }

    @Test
    public void rejectAddingLastNull() {
        Deque<Object> deque = new Deque<>();
        Assertions.assertThrows(IllegalArgumentException.class,
                                () -> deque.addLast(null));
    }

    @Test
    public void testIterator() {

    }

}
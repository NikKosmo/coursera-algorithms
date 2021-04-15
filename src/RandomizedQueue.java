import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    //    private Node<Item> firstNode;
    private int size = 0;
    private Object[] array = new Object[8];

    public RandomizedQueue() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        checkArgument(item);
        if (size == array.length) {
            Object[] outdatedArray = array;
            array = new Object[size * 2];
            System.arraycopy(outdatedArray, 0, array, 0, size);
        }
        array[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        checkIfQueueIsEmpty();
        int place = StdRandom.uniform(size);
        Object result = array[place];
        array[place] = array[size - 1];
        array[size - 1] = null;
        size--;
        if (array.length / 3 > size) {
            Object[] outdatedArray = array;
            array = new Object[array.length / 3];
            System.arraycopy(outdatedArray, 0, array, 0, size);
        }
        return (Item) result;
    }

    public Item sample() {
        checkIfQueueIsEmpty();
        return (Item) array[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private int elementsLeft = size;
            private final Object[] randomOrder = createRandomOrder();

            private Object[] createRandomOrder() {
                Object[] randomOrder = new Object[size];
                System.arraycopy(array, 0, randomOrder, 0, size);
                StdRandom.shuffle(randomOrder);
                return randomOrder;
            }

            @Override
            public boolean hasNext() {
                return elementsLeft != 0;
            }

            @Override
            public Item next() {
                if (hasNext()) {
                    return (Item) randomOrder[--elementsLeft];
                }
                throw new NoSuchElementException();
            }
        };
    }

    private void checkArgument(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkIfQueueIsEmpty() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // do nothing
    }

}

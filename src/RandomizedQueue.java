import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node<Item> firstNode;
    private int size = 0;

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
        firstNode = firstNode != null ?
                Node.createAfter(item, firstNode) :
                Node.of(item);
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        checkIfQueueIsEmpty();
        Node<Item> currentNode = getRandomNode();
        if (currentNode == firstNode) {
            firstNode = currentNode.getPreviousNode();
        }
        currentNode.unlink();
        size--;
        return currentNode.getItem();
    }

    public Item sample() {
        checkIfQueueIsEmpty();
        Node<Item> currentNode = getRandomNode();
        return currentNode.getItem();
    }

    private Node<Item> getRandomNode() {
        int randomPosition = StdRandom.uniform(size);
        return getTargetNode(randomPosition);
    }

    private Node<Item> getTargetNode(int targetPosition) {
        Node<Item> currentNode = firstNode;
        for (int i = 1; i <= targetPosition; i++) {
            currentNode = currentNode.getPreviousNode();
        }
        return currentNode;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private int elementsLeft = size;
            private final int[] randomOrder = createRandomOrder();

            private int[] createRandomOrder() {
                int[] randomOrder = new int[size];
                for (int i = 0; i < size; i++) {
                    randomOrder[i] = i;
                }
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
                    return getTargetNode(randomOrder[--elementsLeft]).getItem();
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

    private static class Node<Item> {
        private Node<Item> nextNode;
        private Node<Item> previousNode;
        private Item item;

        public Node<Item> getNextNode() {
            return nextNode;
        }

        public Node<Item> getPreviousNode() {
            return previousNode;
        }

        public Item getItem() {
            return item;
        }

        public void unlinkPrevious() {
            if (previousNode != null) {
                previousNode.unlinkNext();
                previousNode = null;
            }
        }

        public void unlinkNext() {
            if (nextNode != null) {
                nextNode.unlinkPrevious();
                nextNode = null;
            }
        }

        public void unlink() {
            if (nextNode != null) {
                nextNode.previousNode = previousNode;
            }
            if (previousNode != null) {
                previousNode.nextNode = nextNode;
            }
        }

        static <Item> Node<Item> of(Item item) {
            Node<Item> node = new Node<>();
            node.item = item;
            return node;
        }

        static <Item> Node<Item> createBefore(Item item, Node<Item> nextNode) {
            Node<Item> node = Node.of(item);
            node.nextNode = nextNode;
            nextNode.previousNode = node;
            return node;
        }

        static <Item> Node<Item> createAfter(Item item, Node<Item> previousNode) {
            Node<Item> node = Node.of(item);
            node.previousNode = previousNode;
            previousNode.nextNode = node;
            return node;
        }
    }
}

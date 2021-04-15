import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> firstNode;
    private Node<Item> lastNode;
    private int size = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        checkArgument(item);
        if (size == 0) {
            addInitialItem(item);
        } else {
            firstNode = Node.createBefore(item, firstNode);
        }
        size++;
    }

    public void addLast(Item item) {
        checkArgument(item);
        if (size == 0) {
            addInitialItem(item);
        } else {
            lastNode = Node.createAfter(item, lastNode);
        }
        size++;
    }

    private void addInitialItem(Item item) {
        Node<Item> initialNode = Node.of(item);
        firstNode = initialNode;
        lastNode = initialNode;
    }

    private void checkArgument(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    public Item removeFirst() {
        checkIfDequeIsEmpty();
        Node<Item> currentNode = this.firstNode;
        if (size == 1) {
            cleanNodes();
        } else {
            firstNode = firstNode.getNextNode();
            firstNode.unlinkPrevious();
        }
        size--;
        return currentNode.getItem();
    }

    public Item removeLast() {
        checkIfDequeIsEmpty();
        Node<Item> currentNode = this.lastNode;
        if (size == 1) {
            cleanNodes();
        } else {
            lastNode = lastNode.getPreviousNode();
            lastNode.unlinkNext();
        }
        size--;
        return currentNode.getItem();
    }

    private void cleanNodes() {
        firstNode = null;
        lastNode = null;
    }

    private void checkIfDequeIsEmpty() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private Node<Item> currentNode = firstNode;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Item next() {
                if (currentNode != null) {
                    Item item = currentNode.getItem();
                    currentNode = currentNode.getNextNode();
                    return item;
                }
                throw new NoSuchElementException();
            }
        };
    }

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
                Node<Item> unlinkedNode = previousNode;
                previousNode = null;
                unlinkedNode.unlinkNext();
            }
        }

        public void unlinkNext() {
            if (nextNode != null) {
                Node<Item> unlinkedNode = nextNode;
                nextNode = null;
                unlinkedNode.unlinkPrevious();
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

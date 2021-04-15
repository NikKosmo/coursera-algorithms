import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        int outputSize = Integer.parseInt(args[0]);
        Iterator<String> iterator = queue.iterator();
        for (int i = 0; i < outputSize; i++) {
            System.out.println(iterator.next());
        }
    }
}
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {
        String champion = null;
        int wordCounter = 1;
        while (!StdIn.isEmpty()) {
            String currentString = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / wordCounter)) {
                champion = currentString;
            }
            wordCounter++;
        }
        System.out.println(champion);
    }
}

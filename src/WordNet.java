import java.io.File;

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        checkArgument(synsets);
        checkArgument(hypernyms);
        File synsetsFile = new File(synsets);
        File hypernymsFile = new File(synsets);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        checkArgument(word);
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkArgument(nounA);
        checkArgument(nounB);
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkArgument(nounA);
        checkArgument(nounB);
        return null;
    }

    private void checkArgument(Object item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }


    // do unit testing of this class
    public static void main(String[] args) {
        new WordNet("synsets.txt", "hypernyms.txt");
    }
}
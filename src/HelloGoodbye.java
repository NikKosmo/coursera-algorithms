public class HelloGoodbye {

    public static void main(String[] args) {
        String first = args[0];
        String second = args[1];
        System.out.println(String.format("Hello %s and %s.", first, second));
        System.out.println(String.format("Goodbye %s and %s.", second, first));
    }
}

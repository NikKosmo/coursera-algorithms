import java.util.stream.IntStream;

public class FizzBuzz {

    public static void main(String[] args) {
        IntStream.range(0, 100)
                .mapToObj(FizzBuzz::fizzBuzz)
                .forEach(System.out::println);
    }

    public static String fizzBuzz(int n) {
        String result = "";
        if (n % 3 == 0) {
            result += "Fizz";
        }
        if (n % 5 == 0) {
            result += "Buzz";
        }
        return result.isEmpty() ?
                String.valueOf(n) :
                result;
    }

}

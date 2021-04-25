import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FastCollinearPointsTest {

    @Test
    public void fiveByFive() {
        Point[] points = new Point[25];
        int counter = 0;
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                points[counter++] = new Point(i, j);
            }
        }
        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        System.out.println(fastCollinearPoints.numberOfSegments());
        System.out.println(Arrays.toString(fastCollinearPoints.segments()));
        assertEquals(16, fastCollinearPoints.numberOfSegments());
    }


}
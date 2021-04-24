public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = 1; j < points.length - 2; j++) {

                for (int k = 2; k < points.length - 1; k++) {
                    for (int l = 3; l < points.length; l++) {

                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }
}
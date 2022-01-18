package kmeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a set of points in any dimensions
 */
public class DataPointSet {

    private final List<DataPoint> points = new ArrayList<DataPoint>();

    public void addPoint(final DataPoint point) {
        points.add(point);
    }

    public int getNumberOfPoints() {
        return points.size();
    }

    /**
     * Calculate the mean point of the set
     * @return
     */
    public DataPoint meanPoint() {
        if (points.size() == 0) throw new IllegalStateException("No points to generate mean for");

        final DataPoint meanPoint = new DataPoint(new double[points.get(0).getDimension()]);

        for (DataPoint point : points) {
            meanPoint.add(point);
        }
        meanPoint.divide(points.size());

        return meanPoint;
    }

    /**
     * Get the minimum and maximum coordinate in all dimensions.
     * (These points have the effect of creating a bounding box around the points)
     * @return 0 index - minimum value of all coordinates
     *          1 index - maximum value of all coordinates
     */
    public DataPoint[] getMinMax() {
        final DataPoint[] minMax = new DataPoint[2];
        minMax[0] = points.get(0).copy();
        minMax[1] = points.get(0).copy();

        for (DataPoint point : points) {
            minMax[0].min(point);
            minMax[1].max(point);
        }
        return minMax;
    }

    /**
     * Organise the set of points into distinct sets by which given centroid they are closest to
     * @param centroids
     * @return
     */
    public DataPointSet[] sortPointsByClosest(final DataPoint[] centroids) {
        final DataPointSet[] pointsClosestToCentroid = new DataPointSet[centroids.length];

        for (int i = 0; i < centroids.length; i++) {
            pointsClosestToCentroid[i] = new DataPointSet();
        }

        for (DataPoint point : points) {

            double shortestDistanceToCentroid = point.distanceFrom(centroids[0]);
            int indexOfClosestCentroid = 0;

            for (int i = 1; i < centroids.length; i++) {

                double distanceFromCurrentCentroid = point.distanceFrom(centroids[i]);

                if (distanceFromCurrentCentroid < shortestDistanceToCentroid) {
                    indexOfClosestCentroid = i;
                    shortestDistanceToCentroid = distanceFromCurrentCentroid;
                }
            }

            pointsClosestToCentroid[indexOfClosestCentroid].addPoint(point);
        }

        return pointsClosestToCentroid;
    }

    public List<DataPoint> getPoints() {
        return points;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof DataPointSet)) return false;
        return points.equals(((DataPointSet) obj).getPoints());
    }

}

package kmeans;

import java.util.Random;

/**
 * Algorithm to compute k-centroids for a given set of points in any dimension
 */
public class KMeans {

    private final int k;
    private final DataPointSet points;

    private final DataPoint[] centroids;

    /**
     * @param k number of centroids
     * @param points Set of points to run algorithm against
     *               Assumes all points have same dimension
     */
    public KMeans(final int k, final DataPointSet points) {
        if (points.getNumberOfPoints() == 0) throw new IllegalArgumentException("Can't run kMeans against 0 points");

        this.k = k;
        this.points = points;
        centroids = new DataPoint[k];

        final DataPoint[] minMax = points.getMinMax();
        initRandomCentroids(minMax[0], minMax[1]);
    }

    /**
     * Initialise k centroids within the given min/max bounds
     * @param min
     * @param max
     */
    private void initRandomCentroids(final DataPoint min, final DataPoint max) {
        for (int i = 0; i < k; i++) {
            centroids[i] = createRandomPoint(min, max);
        }
    }

    /**
     * Compute a random point within the given min/max bounds
     * @param min
     * @param max
     * @return
     */
    private DataPoint createRandomPoint(final DataPoint min, final DataPoint max) {
        double[] coordinates = new double[points.getPoints().get(0).getDimension()];
        for (int j = 0; j < coordinates.length; j++) {
            coordinates[j] = ((new Random().nextDouble())*(max.getCoordinate(j) - min.getCoordinate(j)))
                    + min.getCoordinate(j);
        }
        return new DataPoint(coordinates);
    }

    public DataPoint[] run() {
        mainAlgorithmLoop();
        return centroids;
    }

    private void mainAlgorithmLoop() {
        DataPointSet[] oldClusters = null;
        DataPointSet[] newClusters = points.sortPointsByClosest(centroids);
        while (!equalClusters(oldClusters, newClusters)) {
            oldClusters = newClusters;
            calculateNewCentroids(oldClusters);
            newClusters = points.sortPointsByClosest(centroids);
        }
    }

    /**
     * For each set of
     * @param clusters
     */
    private void calculateNewCentroids(final DataPointSet[] clusters) {
        for (int i = 0; i < k; i++) {
            centroids[i] = clusters[i].meanPoint();
        }
    }

    private boolean equalClusters(final DataPointSet[] set1, final DataPointSet[] set2) {
        if (set1 == null || set2 == null) return false;

        for (int i = 0; i < set1.length; i++) {
            if (!set1[i].equals(set2[i])) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        DataPointSet points = new DataPointSet();
        points.addPoint(new DataPoint(1.0, 1.0));
        points.addPoint(new DataPoint(2.0, 1.0));
        points.addPoint(new DataPoint(1.0, 2.0));
        points.addPoint(new DataPoint(2.0, 2.0));


        points.addPoint(new DataPoint(9.0, 8.0));
        points.addPoint(new DataPoint(8.0, 9.0));
        points.addPoint(new DataPoint(8.0, 8.0));
        points.addPoint(new DataPoint(9.0, 9.0));

        KMeans alg = new KMeans(2, points);
        DataPoint[] centroids = alg.run();

        for(DataPoint centroid : centroids) {
            System.out.println(centroid.toString());
        }
    }

}

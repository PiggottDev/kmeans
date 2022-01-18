package kmeans;

import java.util.Arrays;

/**
 * Class representing a point in any dimension
 */
public class DataPoint {

    private final double[] coordinates;

    public DataPoint(final double... coordinates) {
        if (coordinates == null) throw new IllegalArgumentException("coordinates can not be null");
        this.coordinates = coordinates;
    }

    /**
     * Get the coordinate for a given dimension/axis
     * @param dimension
     * @return
     */
    public double getCoordinate(final int dimension) {
        return coordinates[dimension];
    }

    /**
     * Get the number of coordinates/dimension of the point
     * @return
     */
    public int getDimension() {
        return coordinates.length;
    }

    /**
     * Add all the coordinates of a given point to this point
     * @param point
     */
    public void add(final DataPoint point) {
        for (int i = 0; i < getDimension(); i++) {
            coordinates[i] += point.getCoordinate(i);
        }
    }

    /**
     * Divide all coordinates by a given value
     * @param value
     */
    public void divide(final int value) {
        for (int i = 0; i < getDimension(); i++) {
            coordinates[i] /= value;
        }
    }

    /**
     * Create the maximum value across all dimensions.
     * @param point
     */
    public void max(final DataPoint point) {
        for (int i = 0; i < getDimension(); i++) {
            if (point.getCoordinate(i) > coordinates[i]) coordinates[i] = point.getCoordinate(i);
        }
    }

    /**
     * Create the minimum value across all dimensions.
     * @param point
     */
    public void min(final DataPoint point) {
        for (int i = 0; i < getDimension(); i++) {
            if (point.getCoordinate(i) < coordinates[i]) coordinates[i] = point.getCoordinate(i);
        }
    }

    /**
     * Calculate the cartesian distance from the given point
     * @param point
     * @return
     */
    public double distanceFrom(final DataPoint point) {
        double squaredDistance = 0.0;
        for (int i = 0; i < getDimension(); i++) {
            double difference = coordinates[i] - point.getCoordinate(i);
            squaredDistance += difference * difference;
        }
        return Math.sqrt(squaredDistance);
    }

    public DataPoint copy() {
        return new DataPoint(Arrays.copyOf(coordinates, getDimension()));
    }

    /**
     * Remove the last coordinates to create a point with only given dimensions
     * @param dimensions
     * @return
     */
    public DataPoint truncate(final int dimensions) {
        return new DataPoint(Arrays.copyOf(coordinates, dimensions));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(coordinates[0]);
        for (int i = 1; i < coordinates.length; i++) {
            str.append(","  + coordinates[i]);
        }
        return str.toString();
    }

}

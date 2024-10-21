package edu.luc.etl.cs313.android.shapes.model;

/**
 * A point, implemented as a location without a shape.
 */
public class Point extends Location {

    // A point is a location with a shape of a circle with radius 0
    public Point(final int x, final int y) {
        super(x, y, new Circle(0)); // Circle with radius 0
        assert x >= 0;
        assert y >= 0;
    }

    // Since a Point has no real shape (Circle with radius 0), it overrides some behavior if needed.
}

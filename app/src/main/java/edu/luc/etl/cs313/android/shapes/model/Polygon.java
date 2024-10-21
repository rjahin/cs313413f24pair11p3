package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A special case of a group consisting only of Points.
 */
public class Polygon extends Group {

    public Polygon(final Point... points) {
        super(points); // Use Group's constructor, but only accept Points
    }

    @SuppressWarnings("unchecked")
    public List<? extends Point> getPoints() {
        return (List<? extends Point>) getShapes(); // Cast the shapes to Points
    }

    @Override
    public <Result> Result accept(final Visitor<Result> v) {
        return v.onPolygon(this);  // Delegate to the visitor's onPolygon method
    }
}

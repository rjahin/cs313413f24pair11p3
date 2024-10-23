package edu.luc.etl.cs313.android.shapes.model;

public class Count implements Visitor<Integer> {
    private int count = 0;

    @Override
    public Integer onPolygon(final Polygon p) {
        return 1; // A polygon is a basic shape
    }

    @Override
    public Integer onCircle(final Circle c) {
        return 1; // A circle is a basic shape
    }

    @Override
    public Integer onGroup(final Group g) {
        int count = 0;
        for (Shape shape : g.getShapes()) {
            count += shape.accept(this); // Recursively count the shapes
        }
        return count;
    }


    @Override
    public Integer onRectangle(final Rectangle r) {
        return 1; // A rectangle is a basic shape
    }

    @Override
    public Integer onOutline(final Outline o) {
        return o.getShape().accept(this); // Count the shape inside the outline
    }

    @Override
    public Integer onFill(final Fill f) {
        return f.getShape().accept(this); // Count the shape inside the fill
    }

    @Override
    public Integer onLocation(final Location l) {
        return l.getShape().accept(this); // Count the shape at this location
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this); // Count the shape inside the stroke color
    }
}

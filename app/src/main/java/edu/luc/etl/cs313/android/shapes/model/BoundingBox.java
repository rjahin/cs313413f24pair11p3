package edu.luc.etl.cs313.android.shapes.model;

public class BoundingBox implements Visitor<Location> {

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this); // Delegate to the enclosed shape
    }

    @Override
    public Location onGroup(final Group g) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (Shape s : g.getShapes()) {
            Location loc = s.accept(this);
            Rectangle bbox = (Rectangle) loc.getShape();
            minX = Math.min(minX, loc.getX());
            minY = Math.min(minY, loc.getY());
            maxX = Math.max(maxX, loc.getX() + bbox.getWidth());
            maxY = Math.max(maxY, loc.getY() + bbox.getHeight());
        }
        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }

    @Override
    public Location onLocation(final Location l) {
        Location innerLoc = l.getShape().accept(this);
        return new Location(l.getX() + innerLoc.getX(), l.getY() + innerLoc.getY(), innerLoc.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0, 0, r); // A rectangle is its own bounding box
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this); // Delegate to the enclosed shape
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this); // Delegate to the enclosed shape
    }

    @Override
    public Location onPolygon(final Polygon s) {
        // Calculate the bounding box of the polygon
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (Point p : s.getPoints()) {
            minX = Math.min(minX, p.getX());
            minY = Math.min(minY, p.getY());
            maxX = Math.max(maxX, p.getX());
            maxY = Math.max(maxY, p.getY());
        }
        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }
}

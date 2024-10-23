package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

import java.util.List;

public class Draw implements Visitor<Void> {

    private final Canvas canvas;
    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas;
        this.paint = paint;
        paint.setStyle(Style.STROKE); // Default style is outline
    }

    @Override
    public Void onCircle(final Circle c) {
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor sc) {
        paint.setColor(sc.getColor());

        sc.getShape().accept(this);

        paint.setColor(sc.getColor());
        paint.setStyle(Paint.Style.STROKE);
        return null;

    }

    @Override
    public Void onFill(final Fill f) {
        Paint.Style oldStyle = paint.getStyle();

        paint.setStyle(Style.FILL_AND_STROKE);
        f.getShape().accept(this);

        paint.setStyle(oldStyle);
        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        for (Shape s : g.getShapes()) {
            s.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        canvas.save();  // Save the canvas state before translating
        canvas.translate(l.getX(), l.getY());  // Translate to the location's coordinates
        l.getShape().accept(this);  // Draw the shape at the translated position
        canvas.translate(-l.getX(), -l.getY());  // Restore the canvas to its original state after drawing
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);  // Draw the rectangle
        return null;
    }

    @Override
    public Void onOutline(final Outline o) {
        paint.setStyle(Style.STROKE);
        o.getShape().accept(this);
        paint.setStyle(Style.STROKE);
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        List<? extends Point> points = s.getPoints();
        if (points.size() > 1) {
            float[] pts = new float[s.getPoints().size() * 4];// Array of line segments
            int i = 0;
            for (int j = 0; j < s.getPoints().size(); j++) {
                Point p1 = s.getPoints().get(j);
                Point p2 = s.getPoints().get((j + 1) % s.getPoints().size());
                pts[i++] = p1.getX();
                pts[i++] = p1.getY();
                pts[i++] = p2.getX();
                pts[i++] = p2.getY();
            }
            canvas.drawLines(pts, paint);
            Point first = points.get(0);
            Point last = points.get(points.size() - 1);
            canvas.drawLine(last.getX(), last.getY(), first.getX(), first.getY(), paint);
        }
        return null;
    }
}


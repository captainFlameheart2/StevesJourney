package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class CirclePlatform extends Platform {

    private double radius;

    public CirclePlatform(Display display, ArrayList<CircleBody> affectableCircleBodies, Vector2D pos, double angle, double radius) {
        super(display, affectableCircleBodies, pos, angle);
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector2D closestPoint(CircleBody cb) {
//        Vector2D pos = pos();
        Vector2D closestPoint = Vector2D.sub(cb.POS, POS);
        closestPoint.limit(radius);
        closestPoint.add(POS);
        return closestPoint;
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.circleWithinPaintDist(POS, radius, DISPLAY.CAMERA);
    }

    @Override
    public Shape mainPaintingShape() {
        double diameter = 2 * radius;
        return new Ellipse2D.Double(-radius, -radius, diameter, diameter);
    }

}

package steve.s.journey;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author jonat
 */
public abstract class CircleParticle extends Particle {

    public final double RADIUS, DIAMETER;
    
    public CircleParticle(Display display, int lifespan, double lifespanLerpVal, Vector2D pos, Vector2D vel, double radius) {
        super(display, lifespan, lifespanLerpVal, pos, vel);
        RADIUS = radius;
        DIAMETER = 2 * RADIUS;
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.circleWithinPaintDist(pos(), RADIUS, DISPLAY.CAMERA);
    }

    @Override
    public Shape mainPaintingShape() {
        return new Ellipse2D.Double(-RADIUS, -RADIUS, DIAMETER, DIAMETER);
    }
    
}

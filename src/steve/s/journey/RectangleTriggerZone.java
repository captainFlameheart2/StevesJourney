package steve.s.journey;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jonat
 */
public class RectangleTriggerZone extends TriggerZone {

    private final Vector2D SIZE, HALF_SIZE;
    private double angle;

    public RectangleTriggerZone(Display display, Vector2D pos, Vector2D size) {
        super(display, pos);
        SIZE = size;
        HALF_SIZE = Vector2D.div(SIZE, 2);
    }

    @Override
    public boolean containsActivationPoint(Vector2D activationPoint) {
        return Paintable.closestRectPointToOtherPoint(pos(), SIZE, angle, activationPoint).equals(activationPoint, .1);
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.rectWithinPaintDist(pos(), SIZE, angle, DISPLAY.CAMERA);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = super.mainPaintingGraphics(sourceGraphics);
        mainPaintingGraphics.rotate(angle);
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Rectangle2D.Double(-HALF_SIZE.x, -HALF_SIZE.y, SIZE.x, SIZE.y);
    }

}

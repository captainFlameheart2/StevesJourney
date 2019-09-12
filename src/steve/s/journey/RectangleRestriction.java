package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jonat
 */
public class RectangleRestriction extends Restriction {

    private final Vector2D SIZE, HALF_SIZE;

    public RectangleRestriction(Display display, Vector2D pos, double angle, Vector2D size) {
        super(display, pos, angle);
        SIZE = size;
        HALF_SIZE = Vector2D.div(SIZE, 2);
    }

    @Override
    public Vector2D closestPoint(Platform p) {
        return Paintable.closestRectPointToOtherPoint(POS, SIZE, getAngle(), p.POS);
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.rectWithinPaintDist(POS, SIZE, getAngle(), DISPLAY.CAMERA);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        Vector2D translation = new Vector2D(POS);
        mainPaintingGraphics.translate(translation.x, translation.y);
        mainPaintingGraphics.rotate(getAngle());
        mainPaintingGraphics.setStroke(new BasicStroke(3));
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Rectangle2D.Double(-HALF_SIZE.x, -HALF_SIZE.y, SIZE.x, SIZE.y);
    }
}

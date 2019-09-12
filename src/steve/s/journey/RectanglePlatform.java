package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class RectanglePlatform extends Platform {

    private final Vector2D SIZE, HALF_SIZE;

    public RectanglePlatform(Display display, ArrayList<CircleBody> affectableCircleBodies, Vector2D pos, double angle, Vector2D size) {
        super(display, affectableCircleBodies, pos, angle);
        SIZE = size;
        HALF_SIZE = Vector2D.div(SIZE, 2);
    }

    @Override
    public Vector2D closestPoint(CircleBody cb) {
        return Paintable.closestRectPointToOtherPoint(POS, SIZE, getAngle(), cb.POS);
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

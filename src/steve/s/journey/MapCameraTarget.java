package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jonat
 */
public class MapCameraTarget implements Updatable, Paintable {

    private final Steve STEVE;
    public final Vector2D POS;
    private final Vector2D SIZE, HALF_SIZE;

    public MapCameraTarget(Steve steve, Vector2D size) {
        STEVE = steve;
        POS = new Vector2D(STEVE.POS);
        SIZE = size;
        HALF_SIZE = Vector2D.div(SIZE, 2);
    }

    @Override
    public void update() {
        POS.limitComponents(STEVE.POS.x - HALF_SIZE.x, STEVE.POS.x + HALF_SIZE.x, STEVE.POS.y - HALF_SIZE.y, STEVE.POS.y + HALF_SIZE.y);
    }

    @Override
    public boolean whitinPaintingDist() {
        return true;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).draw(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.translate(POS.x, POS.y);
        mainPaintingGraphics.setStroke(new BasicStroke(3));
        mainPaintingGraphics.setColor(Color.RED);
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Rectangle2D.Double(-HALF_SIZE.x, -HALF_SIZE.y, SIZE.x, SIZE.y);
    }

}

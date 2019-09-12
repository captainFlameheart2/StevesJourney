package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jonat
 */
public class GrassParticle extends Particle {

    private final Vector2D SIZE = new Vector2D(StevesJourney.random(5, 15), StevesJourney.random(5, 15)),
            HALF_SIZE = Vector2D.div(SIZE, 2);
    private double angle;
    private final double ANGLE_CHANGE = StevesJourney.random(-Math.PI / 10, Math.PI / 10);

    public GrassParticle(Display display, Vector2D pos, Vector2D vel) {
        super(display, 255, .1, pos, vel);
    }

    @Override
    public void update() {
        super.update();
        angle += ANGLE_CHANGE;
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.rectWithinPaintDist(pos(), SIZE, angle, DISPLAY.CAMERA);
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).fill(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = super.mainPaintingGraphics(sourceGraphics);
        mainPaintingGraphics.rotate(angle);
        mainPaintingGraphics.setColor(new Color(0, 150, 0, getLifespan()));
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Rectangle2D.Double(-HALF_SIZE.x, -HALF_SIZE.y, SIZE.x, SIZE.y);
    }

}

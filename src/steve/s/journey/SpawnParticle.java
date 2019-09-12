package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author jonat
 */
public class SpawnParticle extends CircleParticle {

//    private final double RADIUS = StevesJourney.random(10, 30), DIAMETER = 2 * RADIUS;

    public SpawnParticle(Display display, Vector2D pos, Vector2D vel) {
        super(display, (int) StevesJourney.random(150, 255), .05, pos, vel, StevesJourney.random(10, 30));
    }

    public static SpawnParticle particleWithRandomDirection(Display display, Vector2D pos) {
        return new SpawnParticle(display, pos, Vector2D.random2D(StevesJourney.random(3, 5)));
    }

    @Override
    public boolean whitinPaintingDist() {
        return true;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).fill(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = super.mainPaintingGraphics(sourceGraphics);
        mainPaintingGraphics.setColor(new Color(255, 0, 255, getLifespan()));
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Ellipse2D.Double(-RADIUS, -RADIUS, DIAMETER, DIAMETER);
    }

}

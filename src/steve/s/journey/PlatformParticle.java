package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author jonat
 */
public class PlatformParticle extends CircleParticle {

    private final Color COLOR;

    public PlatformParticle(Display display, Vector2D pos, Color color) {
        super(display, 255, .05, pos, Vector2D.random2D(), StevesJourney.random(15));
        COLOR = color;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).fill(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = super.mainPaintingGraphics(sourceGraphics);
        mainPaintingGraphics.setColor(StevesJourney.transparencyMod(COLOR, getLifespan()));
        return mainPaintingGraphics;
    }

}

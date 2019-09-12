package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

/**
 *
 * @author jonat
 */
public class JumpParticle extends CircleParticle {

    public JumpParticle(Display display, Vector2D pos) {
        super(display, 255, .05, pos, new Vector2D(StevesJourney.random(-5, 5), 0), StevesJourney.random(5, 20));
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).fill(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = super.mainPaintingGraphics(sourceGraphics);
        mainPaintingGraphics.setColor(StevesJourney.transparencyMod(Color.WHITE, getLifespan()));
        return mainPaintingGraphics;
    }

}

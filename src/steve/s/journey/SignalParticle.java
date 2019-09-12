package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author jonat
 */
public class SignalParticle extends RectangleParticle {

    public SignalParticle(Display display, Vector2D pos) {
        super(
                display,
                .5,
                pos,
                Vector2D.random2D(.1),
                new Vector2D(StevesJourney.random(5, 15), StevesJourney.random(5, 15)),
                StevesJourney.random(-Math.PI / 50, Math.PI / 50)
        );
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = super.mainPaintingGraphics(sourceGraphics);
        mainPaintingGraphics.setColor(new Color(0, 0, 255, 255));
        return mainPaintingGraphics;
    }

}

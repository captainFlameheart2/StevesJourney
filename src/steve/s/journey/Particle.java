package steve.s.journey;

import java.awt.Graphics2D;
import java.awt.Shape;

/**
 *
 * @author jonat
 */
public abstract class Particle implements Updatable, Paintable {
    
    public final Display DISPLAY;
    
    private int lifespan;
    private final double LIFESPAN_LERP_VAL;

    private final Vector2D POS, VEL;

    public Particle(Display display, int lifespan, double lifespanLerpVal, Vector2D pos, Vector2D vel) {
        DISPLAY = display;
        
        this.lifespan = lifespan;
        LIFESPAN_LERP_VAL = lifespanLerpVal;
        POS = new Vector2D(pos);
        VEL = vel;
        Updatable.addMember(this, 0);
        Paintable.addMember(this, 0);
    }
    
    public Vector2D pos() {
        return new Vector2D(POS);
    }

    public int getLifespan() {
        return lifespan;
    }

    @Override
    public void update() {
        lifespan = (int) StevesJourney.lerp(lifespan, -10, LIFESPAN_LERP_VAL);
        if (lifespan <= 0) {
            Updatable.removeMember(this, 0);
            Paintable.removeMember(this, 0);
        } else {
            POS.add(VEL);
        }
    }

    @Override
    public abstract boolean whitinPaintingDist();

    @Override
    public abstract void paint(Graphics2D sourceGraphics);

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.translate(POS.x, POS.y);
        return mainPaintingGraphics;
    }

    @Override
    public abstract Shape mainPaintingShape();

}

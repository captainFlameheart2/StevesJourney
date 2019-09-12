package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public abstract class Restriction implements Updatable, Paintable {

    public final Display DISPLAY;

    public final ArrayList<Platform> AFFECTABLE_PLATFORMS = new ArrayList<>();

    public final Vector2D POS, VEL = new Vector2D();
    private double angle, angleChange;

    public Restriction(Display display, Vector2D pos, double angle) {
        DISPLAY = display;

        POS = pos;
        this.angle = angle;
    }
    
    public double getAngle() {
        return angle;
    }

    @Override
    public void update() {
        handleRestriction();
    }

    private void handleRestriction() {
        for (Platform affectablePlatform : AFFECTABLE_PLATFORMS) {
            Vector2D closestPoint = closestPoint(affectablePlatform);
            Vector2D offset = Vector2D.sub(affectablePlatform.POS, closestPoint);
            if (offset.mag() > 0) {
                affectablePlatform.POS.set(closestPoint);

                double offsetAngle = offset.angle();
                Vector2D restrictionForce = Vector2D.setMag(offset, -1.1 * affectablePlatform.VEL.xFromAngle(offsetAngle));
                ((PhysicalMovementTreater) affectablePlatform.movementTreater).applyForce(restrictionForce);
            }
        }
    }

    public abstract Vector2D closestPoint(Platform p);

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).draw(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics;
        mainPaintingGraphics.translate(POS.x, POS.y);
        mainPaintingGraphics.rotate(angle);
        mainPaintingGraphics.setColor(Color.BLACK);
        return mainPaintingGraphics;
    }

}

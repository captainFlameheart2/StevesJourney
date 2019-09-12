package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public abstract class TriggerZone implements Updatable, Paintable {

    public final Display DISPLAY;

    private boolean activated;
    public final ArrayList<Vector2D> ACTIVATION_POINTS = new ArrayList<>();
    public final CableEntryPort PORT;
    private Color color;

    private final Vector2D POS, VEL = new Vector2D();

    private float textureOffset;

    public TriggerZone(Display display, Vector2D pos) {
        DISPLAY = display;

        POS = pos;
        PORT = new CableEntryPort(DISPLAY, POS);
        deactivate();
    }

    public Vector2D pos() {
        return new Vector2D(POS);
    }

    @Override
    public void update() {
        textureOffset += .5;
        if (containsAnyActivationPoint()) {
            if (!activated) {
                activate();
            }
        } else {
            if (activated) {
                deactivate();
            }
        }
        if (activated) {
            PORT.sendSignals();
        }
    }

    private boolean containsAnyActivationPoint() {
        for (Vector2D activationPoint : ACTIVATION_POINTS) {
            if (containsActivationPoint(activationPoint)) {
                return true;
            }
        }
        return false;
    }

    public abstract boolean containsActivationPoint(Vector2D activationPoint);

    private void activate() {
        color = Color.RED;
        activated = true;
    }

    private void deactivate() {
        color = Color.BLUE;
        activated = false;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).draw(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.translate(POS.x, POS.y);
        float[] dash = {0, 5, 10};
        mainPaintingGraphics.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, dash, textureOffset));
        mainPaintingGraphics.setColor(color);
        return mainPaintingGraphics;
    }

}

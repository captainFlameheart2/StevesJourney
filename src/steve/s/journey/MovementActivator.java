package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class MovementActivator implements SignalReciever, Updatable, Paintable {

    private final Display DISPLAY;

    private final Vector2D POS;
    private static final int RADIUS = 30, DIAMETER = 2 * RADIUS;
    public final CableExitPort PORT;
    
    public final ArrayList<MovementTreater> MOVEMENT_TREATERS = new ArrayList<>();
    private long preActivationUpdateCount;

    public MovementActivator(Display display, Vector2D pos) {
        DISPLAY = display;

        POS = pos;
        PORT = new CableExitPort(POS, this);
    }

    @Override
    public void handleRecievedSignal(Signal s) {
        setMovementActivation(true);
        preActivationUpdateCount = Display.getUpdateCount();
    }

    @Override
    public void update() {
//        if (Display.getUpdateCount() - preActivationUpdateCount == 1) {
//            setMovementActivation(false);
//        }
    }

    private void setMovementActivation(boolean active) {
        for (MovementTreater mt : MOVEMENT_TREATERS) {
            mt.active = active;
        }
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.circleWithinPaintDist(POS, RADIUS, DISPLAY.CAMERA);
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingG = mainPaintingGraphics(sourceGraphics);
        Shape mainPaintingShape = mainPaintingShape();
        mainPaintingG.setColor(Color.WHITE);
        mainPaintingG.fill(mainPaintingShape);
        mainPaintingG.setColor(Color.BLACK);
        mainPaintingG.draw(mainPaintingShape);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.translate(POS.x, POS.y);
        mainPaintingGraphics.setStroke(new BasicStroke(4));
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Ellipse2D.Double(-RADIUS, -RADIUS, DIAMETER, DIAMETER);
    }

}

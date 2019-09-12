package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 *
 * @author jonat
 */
public class Cable implements Paintable {

    public final CableEntryPort ENTRY_PORT;
    public final CableExitPort EXIT_PORT;
    private double angle;

    public Cable(CableEntryPort entryPort, CableExitPort exitPort) {
        ENTRY_PORT = entryPort;
        ENTRY_PORT.CONNECTED_CABLES.add(this);
        EXIT_PORT = exitPort;
        assignAngle();
    }

    private void assignAngle() {
        angle = Vector2D.sub(EXIT_PORT.POS, ENTRY_PORT.POS).angle();
    }
    
    public double getAngle() {
        return angle;
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
        mainPaintingGraphics.setStroke(new BasicStroke(2));
        mainPaintingGraphics.setColor(Color.GREEN);
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Line2D.Double(ENTRY_PORT.POS.x, ENTRY_PORT.POS.y, EXIT_PORT.POS.x, EXIT_PORT.POS.y);
    }

}

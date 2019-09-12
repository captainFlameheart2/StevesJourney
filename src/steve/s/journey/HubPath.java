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
public class HubPath implements Updatable, Paintable {

    private final Display DISPLAY;
    
    public final HubSpot HUB_SPOT_A, HUB_SPOT_B;

    private static final int dashValue = 5;
    private float textureOffset;
    

    public HubPath(Display display, HubSpot hubSpotA, HubSpot hubSpotB) {
        DISPLAY = display;
        
        HUB_SPOT_A = hubSpotA;
        HUB_SPOT_B = hubSpotB;
        HUB_SPOT_A.addConnectedSpot(HUB_SPOT_B);
        HUB_SPOT_B.addConnectedSpot(HUB_SPOT_A);
    }

    @Override
    public void update() {
        textureOffset += .5;
    }

    @Override
    public boolean whitinPaintingDist() {
        return LinePlatform.fromEndPoints(HUB_SPOT_A.POS, HUB_SPOT_B.POS, DISPLAY, null, null).whitinPaintingDist();
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).draw(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        float[] dash = {dashValue};
        mainPaintingGraphics.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, textureOffset));
        mainPaintingGraphics.setColor(Color.BLUE);
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Line2D.Double(HUB_SPOT_A.POS.x, HUB_SPOT_A.POS.y, HUB_SPOT_B.POS.x, HUB_SPOT_B.POS.y);
    }

}

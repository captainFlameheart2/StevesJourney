package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class Trail implements Updatable, Paintable {

    private final Vector2D POS;
    public final double HALF_SOURCE_THICKNESS;
    private final Color COLOR;

    public final ArrayList<TrailJunction> JUNCTIONS = new ArrayList<>();

    public Trail(Vector2D pos, double halfSourceThickness, Color color) {
        POS = pos;
        HALF_SOURCE_THICKNESS = halfSourceThickness;
        COLOR = color;
    }

    public Vector2D pos() {
        return new Vector2D(POS);
    }

    @Override
    public void update() {
        if (Display.getUpdateCount() % 2 == 0) {
            TrailJunction.addToTrail(this);
        }
    }

    @Override
    public boolean whitinPaintingDist() {
        return true;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        Shape mainPaintingShape = mainPaintingShape();
        if (mainPaintingShape != null) {
            mainPaintingGraphics(sourceGraphics).fill(mainPaintingShape);
        }
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.setColor(COLOR);
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        int junctionAmount = JUNCTIONS.size();
        if (junctionAmount == 0) {
            return null;
        }

        GeneralPath mainPaintingShape = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        Vector2D startPoint = JUNCTIONS.get(junctionAmount - 1).trailShapeContribution(true);
        mainPaintingShape.moveTo(startPoint.x, startPoint.y);
        for (int i = junctionAmount - 2; i > 0; i--) {
            Vector2D contribution = JUNCTIONS.get(i).trailShapeContribution(true);
            mainPaintingShape.lineTo(contribution.x, contribution.y);
        }
        for (int i = 0; i < junctionAmount - 1; i++) {
            Vector2D contribution = JUNCTIONS.get(i).trailShapeContribution(false);
            mainPaintingShape.lineTo(contribution.x, contribution.y);
        }

        return mainPaintingShape;
    }

}

package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class CustomPlatform extends Platform {

    private final ArrayList<LinePlatform> EDGE_PLATFORMS = new ArrayList<>();
    private final GeneralPath SHAPE = new GeneralPath(GeneralPath.WIND_NON_ZERO);

    public CustomPlatform(Display display, ArrayList<CircleBody> affectableCircleBodies, Vector2D pos, double angle) {
        super(display, affectableCircleBodies, pos, angle);
    }

    public void addVertex(Vector2D relativeVertex) {
        Point2D currentPoint = SHAPE.getCurrentPoint();
        if (currentPoint == null) {
            SHAPE.moveTo(relativeVertex.x, relativeVertex.y);
        } else {
            EDGE_PLATFORMS.add(LinePlatform.fromEndPoints(new Vector2D(currentPoint),
                    relativeVertex,
                    DISPLAY,
                    AFFECTABLE_CIRCLE_BODIES,
                    this));
            SHAPE.lineTo(relativeVertex.x, relativeVertex.y);
        }
    }

    @Override
    public Vector2D closestPoint(CircleBody cb) {
        Vector2D closestPoint = null;
        for (LinePlatform edgePlatform : EDGE_PLATFORMS) {
            Vector2D closestPointOfEdgePlatform = edgePlatform.closestPoint(cb);
            if (closestPoint == null || closestPointOfEdgePlatform.dist(cb.POS) < closestPoint.dist(cb.POS)) {
                closestPoint = closestPointOfEdgePlatform;
            }
        }
        return closestPoint;
    }

    @Override
    public void handleCollision() {
        for (LinePlatform edgePlatform : EDGE_PLATFORMS) {
            edgePlatform.considerOwner();
            edgePlatform.handleCollision();
        }
    }

    @Override
    public boolean whitinPaintingDist() {
        return true;
    }

    @Override
    public Shape mainPaintingShape() {
        return SHAPE;
    }

}

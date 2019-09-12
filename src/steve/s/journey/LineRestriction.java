package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class LineRestriction extends RectangleRestriction {

//    private final CustomPlatform OWNER;
//    private final Vector2D POS_OFFSET;

    public LineRestriction(Display display, Vector2D pos, double angle, double length) {// CustomPlatform owner) {
        super(display, pos, angle, new Vector2D(length, 0));
//        OWNER = owner;
//        POS_OFFSET = (OWNER == null) ? null : new Vector2D(pos);
    }

    public static LinePlatform fromEndPoints(Vector2D endpointA, Vector2D endpointB, Display display, ArrayList<CircleBody> affectableCircleBodies, CustomPlatform owner) {
        Vector2D diff = Vector2D.sub(endpointB, endpointA);
        return new LinePlatform(display, affectableCircleBodies,
                Vector2D.add(endpointA, Vector2D.div(diff, 2)),
                diff.angle(),
                diff.mag(),
                owner);
    }

    public void considerOwner() {
//        if (OWNER != null) {
//            POS.set(Vector2D.add(OWNER.POS, POS_OFFSET));
//            VEL.set(OWNER.VEL);
//        }
    }

}

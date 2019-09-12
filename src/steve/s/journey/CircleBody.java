package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class CircleBody extends CirclePlatform {

//    private final Vector2D G = new Vector2D(0, .3);
    public final Vector2D REFERENCE_VEL = new Vector2D(), RELATIVE_VEL = new Vector2D();
//    private final Vector2D ACC = new Vector2D();

    public ArrayList<CircleBody> ALREADY_AFFECTED_CIRCLE_BODIES = new ArrayList<>();

    public CircleBody(Display display, ArrayList<CircleBody> affectableCircleBodies, Vector2D pos, double angle, double radius) {
        super(display, affectableCircleBodies, pos, angle, radius);
        SET_FORCE_MOVEMENT_TREATER();
    }

    public void setReferenceVel(Vector2D referenceVel) {
        REFERENCE_VEL.set(referenceVel);
        updateRelativeVel();
    }

    private void updateRelativeVel() {
        RELATIVE_VEL.set(Vector2D.sub(VEL, REFERENCE_VEL));
    }

    public void applyForce(Vector2D force) {
        ((PhysicalMovementTreater) movementTreater).applyForce(force);
    }

    @Override
    public void update() {
        ((PhysicalMovementTreater) movementTreater).applyGravity();
        updateRelativeVel();
        angleChange = rollingAngleChange();
        super.update();
    }

    private double rollingAngleChange() {
        return RELATIVE_VEL.x / getRadius();
    }

}

package steve.s.journey;

/**
 *
 * @author jonat
 */
public class WaypointMovementTreater extends MovementTreater {

    private double speed, halfSpeed;

    private final WaypointSet WAYPOINT_SET;
    private Waypoint targetWaypoint;
    public long movementDelayTime;

    public WaypointMovementTreater(Vector2D treatedPos, Vector2D treatedVel, WaypointSet waypointSet) {//
        super(treatedPos, treatedVel);
        WAYPOINT_SET = waypointSet;
        SET_SPEED(5);
        targetWaypoint = WAYPOINT_SET.currentWaypoint();
//        assignVel();
    }

    @Override
    public void update() {
        if (active) {
            if (noMovementDelay()) {
                checkTargetWaypoint();
                super.update();
            } else {
                movementDelayTime -= NS_PER_UPDATE;
                if (noMovementDelay()) {
                    assignVel();
                }
            }
        }
    }

    private void checkTargetWaypoint() {
        if (atTargetWaypoint()) {
            targetWaypoint.performReachingOrders(this);
            assignNewTargetWaypoint();
        }
    }

    private boolean noMovementDelay() {
        return movementDelayTime <= 0;
    }

    private boolean atTargetWaypoint() {
        return targetWaypoint != null && TREATED_POS.dist(targetWaypoint.pos()) <= halfSpeed;
    }

    private void assignNewTargetWaypoint() {
        targetWaypoint = WAYPOINT_SET.nextWaypoint();
        assignVel();
    }

    private void assignVel() {
        if (targetWaypoint == null || !noMovementDelay()) {
            TREATED_VEL.set();
        } else {
            TREATED_VEL.set(Vector2D.sub(targetWaypoint.pos(), TREATED_POS));
            TREATED_VEL.setMag(speed);
        }
    }

    public final void SET_SPEED(double speed) {
        this.speed = speed;
        halfSpeed = .5 * speed;
    }

}

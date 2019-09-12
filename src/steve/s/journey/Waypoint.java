package steve.s.journey;

/**
 *
 * @author jonat
 */
public class Waypoint {

    private final Vector2D POS;
    
    public double reachingSpeedAssignment = 5;
    public long reachingDelay = 5000000000L;

    public Waypoint(Vector2D pos) {
        POS = pos;
    }

    public Vector2D pos() {
        return POS;
    }
    
    public void performReachingOrders(WaypointMovementTreater affectedWMT) {
        affectedWMT.SET_SPEED(reachingSpeedAssignment);
        affectedWMT.movementDelayTime = reachingDelay;
    }

}

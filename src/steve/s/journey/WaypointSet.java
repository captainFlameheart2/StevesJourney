package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class WaypointSet {

    public final ArrayList<WaypointSet> WAYPOINT_SETS = new ArrayList<>();
    public Waypoint onlyWaypoint;

    private int currentIndex = 0;

    public static final int INFINITE_LOOP_AMOUNT = -1;
    private final int LOOP_AMOUNT;
    private int loopIteration;

    public WaypointSet(Waypoint waypoint) {
        this.onlyWaypoint = waypoint;
        LOOP_AMOUNT = 1;
    }

    public WaypointSet(int loopAmount) {
        LOOP_AMOUNT = loopAmount;
    }
    
    public void addWaypoint(Waypoint w) {
        WAYPOINT_SETS.add(new WaypointSet(w));
    }

    public Waypoint nextWaypoint() {
        if (justHasOneWaypoint()) {
            if (increaseCurrentIndex()) {
                return null;
            } else {
                return onlyWaypoint;
            }
        } else {
            WaypointSet ws = currentWaypointSet();
            Waypoint w = ws.nextWaypoint();
            if (w == null) {
                if (increaseCurrentIndex()) {
                    return null;
                }
                return currentWaypointSet().nextWaypoint();
            } else {
                return w;
            }
        }
    }

    private boolean justHasOneWaypoint() {
        return onlyWaypoint != null;
    }

    private boolean increaseCurrentIndex() {
        currentIndex++;
        if (currentIndexTooBig()) {
            return increaseLoopIteration();
        }
        return false;
    }

    private boolean currentIndexTooBig() {
        return (currentIndex >= ((justHasOneWaypoint()) ? 2 : WAYPOINT_SETS.size()));
    }

    private boolean increaseLoopIteration() {
        currentIndex = 0;
        if (LOOP_AMOUNT != INFINITE_LOOP_AMOUNT) {
            loopIteration++;
            if (loopIterationTooBig()) {
                loopIteration = 0;
                return true;
            }
        }
        return false;
    }

    private boolean loopIterationTooBig() {
        return (loopIteration >= LOOP_AMOUNT);
    }

    private WaypointSet currentWaypointSet() {
        return WAYPOINT_SETS.get(currentIndex);
    }
    
    public Waypoint currentWaypoint() {
        if (justHasOneWaypoint()) {
            return onlyWaypoint;
        }
        return currentWaypointSet().currentWaypoint();
    }

}

package steve.s.journey;

/**
 *
 * @author jonat
 */
public class PhysicalMovementTreater extends MovementTreater {

    private static final Vector2D G = new Vector2D(0, .3);
    
    public PhysicalMovementTreater(Vector2D treatedPos, Vector2D treatedVel) {
        super(treatedPos, treatedVel);
    }

    public void applyForce(Vector2D force) {
        TREATED_VEL.add(force);
    }
    
    public void applyGravity() {
        applyForce(G);
    }

    @Override
    public void update() {
        super.update();
    }

}

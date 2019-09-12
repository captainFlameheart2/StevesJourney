package steve.s.journey;

/**
 *
 * @author jonat
 */
public class MovementTreater implements Updatable {

    public final Vector2D TREATED_POS, TREATED_VEL;
    public boolean active;

    public MovementTreater(Vector2D treatedPos, Vector2D treatedVel) {
        TREATED_POS = treatedPos;
        TREATED_VEL = treatedVel;
    }

    @Override
    public void update() {
        TREATED_POS.add(TREATED_VEL);
    }

}

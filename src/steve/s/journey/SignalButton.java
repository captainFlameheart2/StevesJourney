package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class SignalButton extends RectanglePlatform {

//    private final Vector2D 
    
    public SignalButton(Display display, ArrayList<CircleBody> affectableCircleBodies, Vector2D pos, double angle, Vector2D size) {
        super(display, affectableCircleBodies, pos, angle, size);
        SET_FORCE_MOVEMENT_TREATER();
    }

    @Override
    public void update() {
        super.update();
        ((PhysicalMovementTreater) movementTreater).applyForce(new Vector2D(0, -.01));
    }

}

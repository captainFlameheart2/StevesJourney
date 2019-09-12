/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steve.s.journey;

/**
 *
 * @author jonat
 */
public class Signal implements Updatable {

    private final Display DISPLAY;

    private static final double SPEED = 1, HALF_SPEED = .5 * SPEED;
    private final Vector2D POS = new Vector2D(), VEL;

    private final CableExitPort TARGET;

    public Signal(Display display, Cable c) {
        DISPLAY = display;

        VEL = Vector2D.fromAngle(c.getAngle(), SPEED);
        TARGET = c.EXIT_PORT;
        addToScene();
    }

    private void addToScene() {
        Updatable.addMember(this, 0);
    }

    public void removeFromScene() {
        Updatable.removeMember(this, 0);
    }

    @Override
    public void update() {
        if (atTarget()) {
            TARGET.handleRecievedSignal(this);
        } else {
            POS.add(VEL);
//            new SignalParticle(DISPLAY, POS);

        }
    }

    private boolean atTarget() {
        return POS.dist(TARGET.POS) <= HALF_SPEED;
    }

}

package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class CableEntryPort implements SignalReciever {

    private final Display DISPLAY;

    public final Vector2D POS;

    public final ArrayList<Cable> CONNECTED_CABLES = new ArrayList<>();

    public CableEntryPort(Display display, Vector2D pos) {
        DISPLAY = display;

        POS = pos;
    }

    public void sendSignals() {
        handleRecievedSignal(null);
    }

    @Override
    public void handleRecievedSignal(Signal s) {
        for (Cable connectedCable : CONNECTED_CABLES) {
            new Signal(DISPLAY, connectedCable);
        }
    }

}

package steve.s.journey;

/**
 *
 * @author jonat
 */
public class CableExitPort implements SignalReciever {

    public final Vector2D POS;

//    public CableEntryPort connectedPort;
    public final SignalReciever SIGNAL_RECIEVER;

    public CableExitPort(Vector2D pos, SignalReciever signalReciever) {
        POS = pos;
        SIGNAL_RECIEVER = signalReciever;
    }

//    public void handleSignalEntry(Signal s) {
//        if (SIGNAL_RECIEVER != null) {
//            SIGNAL_RECIEVER.handleRecievedSignal();//connectedPort.sendSignals();
//        }
//        s.removeFromScene();
//    }
    @Override
    public void handleRecievedSignal(Signal s) {
        if (SIGNAL_RECIEVER != null) {
            SIGNAL_RECIEVER.handleRecievedSignal(s);//connectedPort.sendSignals();
        }
        s.removeFromScene();
    }

}

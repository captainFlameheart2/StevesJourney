package steve.s.journey;

import java.awt.Graphics2D;

/**
 *
 * @author jonat
 */
public class Camera implements Updatable {

    private final Display DISPLAY;
    
    public static final int UPDATING_ORDER = 2;

    public final Vector2D POS;
    public Vector2D targetPos;

    private double scaling = 1;
    public double targetScaling = 1, scalingLerpVal = .02;
    private long targetScaleResetTimer;

    public Camera(Display display, Vector2D pos) {
        DISPLAY = display;
        POS = pos;
    }

    public double getScaling() {
        return scaling;
    }

    public void resetTargetScaling(long delay) {
        targetScaleResetTimer = delay;
    }
    
    public void resetTargetScaling() {
        targetScaling = 1;
    }

    public void modifyGraphics(Graphics2D g) {
        g.translate(-scaling * POS.x, -scaling * POS.y);
        g.scale(scaling, scaling);
    }
    
    public double paintDist() {
        return 1 / scaling * DISPLAY.halfSize().mag();
    }

    @Override
    public void update() {
        if (!targetScaleResetTimerComplete()) {
            targetScaleResetTimer -= NS_PER_UPDATE;
            if (targetScaleResetTimerComplete()) {
                resetTargetScaling();
            }
        }
        if (targetPos != null) {
            POS.lerp(targetPos, .1);
        }
        scaling = StevesJourney.lerp(scaling, targetScaling, scalingLerpVal);
    }
    
    private boolean targetScaleResetTimerComplete() {
        return targetScaleResetTimer < 0;
    }

}

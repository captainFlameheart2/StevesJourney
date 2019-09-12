package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author jonat
 */
public class HubSteve implements Updatable, KeyInputProcessable, Paintable {

    private final Display DISPLAY;

    public final Vector2D POS, VEL = new Vector2D();
    private static final double SPEED = 5, HALF_SPEED = SPEED / 2;
    private static final double RADIUS = 30, DIAMETER = 2 * RADIUS,
            MOUTH_Y = RADIUS / 3,
            MOUTH_WIDTH = .5 * DIAMETER, MOUTH_HALF_WIDTH = MOUTH_WIDTH / 2,
            MOUTH_HEIGHT = .8 * RADIUS, MOUTH_HALF_HEIGHT = MOUTH_HEIGHT / 2,
            EYE_X = .4 * RADIUS, EYE_Y = -RADIUS / 4,
            EYE_RADIUS = RADIUS / 3, EYE_DIAMETER = 2 * EYE_RADIUS,
            EYE_PUPIL_RADIUS = EYE_RADIUS / 3, EYE_PUPIL_DIAMETER = 2 * EYE_PUPIL_RADIUS;
    private double angle;
    private double angleChange = Math.PI / 50;
    private static final double UPPER_ANGLE_LIMIT = Math.PI / 4, LOWER_ANGLE_LIMIT = -UPPER_ANGLE_LIMIT;

    private HubSpot currentSpot, targetSpot;

    public HubSteve(Display display, HubSpot spawnHubSpot) {
        DISPLAY = display;
        POS = new Vector2D(spawnHubSpot.POS);
        currentSpot = spawnHubSpot;
    }

    @Override
    public void update() {
        if (currentSpot == null) {
            POS.add(VEL);
            if (atTargetSpot()) {
                DISPLAY.AUDIO_PLAYER_STORAGE.HUB_STOPPING_AUDIO_PLAYER.playAudio();
                VEL.set();
                currentSpot = targetSpot;
            } else {
                AudioPlayer steveMoveAudioPlayer = DISPLAY.AUDIO_PLAYER_STORAGE.STEVE_MOVE_AUDIO_PLAYER;
                if (!steveMoveAudioPlayer.CLIP.isRunning()) {
                    steveMoveAudioPlayer.playAudio();
                }
                Vector2D grassParticlePos = Vector2D.setMag(VEL, -1.1 * RADIUS);
                grassParticlePos.rotate(StevesJourney.random(-Math.PI / 3, Math.PI / 3));
                GrassParticle grassParticle = new GrassParticle(DISPLAY, Vector2D.add(POS, grassParticlePos), Vector2D.random2D());
            }
        }
        angle += angleChange;
        if (angle > UPPER_ANGLE_LIMIT) {
            angle = UPPER_ANGLE_LIMIT;
            angleChange *= -1;
        } else if (angle < LOWER_ANGLE_LIMIT) {
            angle = LOWER_ANGLE_LIMIT;
            angleChange *= -1;
        }
    }

    private boolean atTargetSpot() {
        return POS.dist(targetSpot.POS) <= HALF_SPEED;
    }

    @Override
    public void processKeyPress(int keyCode) {
        if (currentSpot != null) {
            double desiredDirectionAngle = Double.NaN;
            switch (keyCode) {
                case KeyEvent.VK_D:
                    desiredDirectionAngle = 0;
                    break;
                case KeyEvent.VK_S:
                    desiredDirectionAngle = Math.PI / 2;
                    break;
                case KeyEvent.VK_A:
                    desiredDirectionAngle = Math.PI;
                    break;
                case KeyEvent.VK_W:
                    desiredDirectionAngle = 1.5 * Math.PI;
                    break;
                case KeyEvent.VK_ENTER:
                    if (currentSpot != null && currentSpot.map != null) {
                        currentSpot.map.transition();
                    }
            }
            if (!Double.isNaN(desiredDirectionAngle)) {
                goToSpot(currentSpot.bestConnectedSpot(desiredDirectionAngle));
            }
        }
    }

    private void goToSpot(HubSpot spot) {
        currentSpot = null;
        targetSpot = spot;
        VEL.set(Vector2D.sub(spot.POS, POS));
        VEL.limit(SPEED);
    }

    @Override
    public void processKeyRelease(int keyCode) {
    }

    @Override
    public boolean whitinPaintingDist() {
        return true;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingG = mainPaintingGraphics(sourceGraphics);
        Ellipse2D mainPaintingShape = (Ellipse2D) mainPaintingShape();

        mainPaintingG.setColor(Color.YELLOW);
        mainPaintingG.fill(mainPaintingShape);
        mainPaintingG.setColor(Color.BLACK);
        mainPaintingG.draw(mainPaintingShape);

        mouthGraphics(mainPaintingG).fill(mouthShape());

        Ellipse2D eyeShape = eyeShape();
        Ellipse2D eyePupilShape = eyePupilShape();
        for (int i = 0; i < 2; i++) {
            Graphics2D eyeG = eyeGraphics(mainPaintingG, 2 * i - 1);
            eyeG.setColor(Color.WHITE);
            eyeG.fill(eyeShape);
            eyeG.setColor(Color.BLACK);
            eyeG.draw(eyeShape);
            eyeG.fill(eyePupilShape);
        }
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.translate(POS.x, POS.y);
        mainPaintingGraphics.rotate(angle);
        if (currentSpot != null) {
            mainPaintingGraphics.scale(1.3, 1.3);
        }
        mainPaintingGraphics.setStroke(new BasicStroke(2));
        return mainPaintingGraphics;
    }

    private Graphics2D mouthGraphics(Graphics2D mainPaintingGraphics) {
        Graphics2D mouthGraphics = (Graphics2D) mainPaintingGraphics.create();
        mouthGraphics.translate(0, MOUTH_Y);
        mouthGraphics.setColor(Color.BLACK);
        return mouthGraphics;
    }

    private Graphics2D eyeGraphics(Graphics2D mainPaintingGraphics, int sideMultiplier) {
        Graphics2D eyeGraphics = (Graphics2D) mainPaintingGraphics.create();
        eyeGraphics.translate(sideMultiplier * EYE_X, EYE_Y);
        return eyeGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Ellipse2D.Double(-RADIUS, -RADIUS, DIAMETER, DIAMETER);
    }

    private Arc2D mouthShape() {
        return new Arc2D.Double(-MOUTH_HALF_WIDTH, -MOUTH_HALF_HEIGHT, MOUTH_WIDTH, MOUTH_HEIGHT, 0, -180, Arc2D.CHORD);
    }

    private Ellipse2D eyeShape() {
        return new Ellipse2D.Double(-EYE_RADIUS, -EYE_RADIUS, EYE_DIAMETER, EYE_DIAMETER);
    }

    private Ellipse2D eyePupilShape() {
        return new Ellipse2D.Double(-EYE_PUPIL_RADIUS, -EYE_PUPIL_RADIUS, EYE_PUPIL_DIAMETER, EYE_PUPIL_DIAMETER);
    }

}

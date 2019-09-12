package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class Steve extends CircleBody {
    
    public boolean jumpAvailable;
    private final int JUMP_Y_REL_VEL_ASSIGNMENT = -15;
    public static final double JUMP_REGAIN_MIN_ANGLE = -3 / 4 * Math.PI, JUMP_REGAIN_MAX_ANGLE = -Math.PI - JUMP_REGAIN_MIN_ANGLE;
    public static final Vector2D G = new Vector2D(0, .3),
            RIGHT_FORCE = new Vector2D(1, 0), LEFT_FORCE = new Vector2D(-RIGHT_FORCE.x, 0);

    public static final double RADIUS = 50, DIAMETER = 2 * RADIUS,
            MOUTH_Y = RADIUS / 3,
            MOUTH_WIDTH = .5 * DIAMETER, MOUTH_HALF_WIDTH = MOUTH_WIDTH / 2,
            MOUTH_HEIGHT = .8 * RADIUS, MOUTH_HALF_HEIGHT = MOUTH_HEIGHT / 2,
            EYE_X = .4 * RADIUS, EYE_Y = -RADIUS / 4,
            EYE_RADIUS = RADIUS / 3, EYE_DIAMETER = 2 * EYE_RADIUS,
            EYE_PUPIL_RADIUS = EYE_RADIUS / 3, EYE_PUPIL_DIAMETER = 2 * EYE_PUPIL_RADIUS;
    
    private final Trail TRAIL;

    public Steve(Display display, ArrayList<CircleBody> affectableCircleBodies, Vector2D pos) {
        super(display, affectableCircleBodies, pos, 0, RADIUS);
        TRAIL = new Trail(POS, RADIUS / 2, new Color(255, 255, 255, 150));
    }
    
    public void addToScene() {
        Paintable.addMember(TRAIL, 0);
        Paintable.addMember(this, 0);
        Updatable.addMember(this, 0);
        Updatable.addMember(TRAIL, 0);
    }

    private Vector2D movementResistance() {
        return new Vector2D(RELATIVE_VEL.x * -.01 * Math.abs(RELATIVE_VEL.x), 0);
    }

    @Override
    public void update() {
        if (KeyHandler.keyHeldDown(KeyEvent.VK_SPACE) || KeyHandler.keyHeldDown(KeyEvent.VK_UP)) {
            tryToJump();
        }
        if (KeyHandler.keyHeldDown(KeyEvent.VK_D) || KeyHandler.keyHeldDown(KeyEvent.VK_RIGHT)) {
            applyForce(RIGHT_FORCE);
        }
        if (KeyHandler.keyHeldDown(KeyEvent.VK_A) || KeyHandler.keyHeldDown(KeyEvent.VK_LEFT)) {
            applyForce(LEFT_FORCE);
        }
        applyForce(movementResistance());
        super.update();
        
        DISPLAY.CAMERA.targetScaling = Math.min(1, 9 / VEL.mag());
    }

    private void tryToJump() {
        if (jumpAvailable) {
            DISPLAY.AUDIO_PLAYER_STORAGE.STEVE_JUMP_AUDIO_PLAYER.playAudio();
            VEL.y = REFERENCE_VEL.y + JUMP_Y_REL_VEL_ASSIGNMENT;
            jumpAvailable = false;
            
            Vector2D jumpParticlePos = new Vector2D(POS);
            jumpParticlePos.y += RADIUS;
            for (int i = 0; i < 20; i++) {
                new JumpParticle(DISPLAY, jumpParticlePos);
            }
        }
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

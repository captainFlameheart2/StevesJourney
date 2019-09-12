package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public abstract class Platform implements Updatable, Paintable {

    public final Display DISPLAY;

    public final ArrayList<CircleBody> AFFECTABLE_CIRCLE_BODIES;
    private final boolean IS_CIRCLE_BODY;
    private final CircleBody THIS_AS_CIRCLE_BODY;

    public final Vector2D POS, VEL = new Vector2D();
    public MovementTreater movementTreater;
    private double angle;
    public double angleChange;

    public Texture texture;

    public Platform(Display display, ArrayList<CircleBody> affectableCircleBodies, Vector2D pos, double angle) {
        DISPLAY = display;

        IS_CIRCLE_BODY = (this instanceof CircleBody);
        THIS_AS_CIRCLE_BODY = (IS_CIRCLE_BODY) ? (CircleBody) this : null;

        AFFECTABLE_CIRCLE_BODIES = affectableCircleBodies;
        POS = pos;
        this.angle = angle;

    }

    public void SET_WAYPOINT_MOVEMENT_TREATER(WaypointSet ws) {
        movementTreater = new WaypointMovementTreater(POS, VEL, ws);
    }

    public final void SET_FORCE_MOVEMENT_TREATER() {
        movementTreater = new PhysicalMovementTreater(POS, VEL);
    }

    public void setTextureFromBufferedImage(BufferedImage i) {
        texture = new Texture(i, POS);
    }

    public double getAngle() {
        return angle;
    }

    @Override
    public void update() {
        if (movementTreater != null) {
            movementTreater.update();
        }
        angle += angleChange;
        handleCollision();
    }

    public void handleCollision() {
        for (CircleBody affectableCircleBody : AFFECTABLE_CIRCLE_BODIES) {
            if (this == affectableCircleBody) {
                continue;
            }
            boolean isCollisionAffectable = isPhysical();
            if (IS_CIRCLE_BODY) {
                if (THIS_AS_CIRCLE_BODY.ALREADY_AFFECTED_CIRCLE_BODIES.contains(affectableCircleBody)) {
                    THIS_AS_CIRCLE_BODY.ALREADY_AFFECTED_CIRCLE_BODIES.remove(affectableCircleBody);
                    continue;
                }
            }

            Vector2D closestPoint = closestPoint(affectableCircleBody);
            Vector2D offset = Vector2D.sub(affectableCircleBody.POS, closestPoint);
            double maxCollisionDist = affectableCircleBody.getRadius();
            if (offset.mag() < maxCollisionDist) {
                affectableCircleBody.setReferenceVel(VEL);
                if (affectableCircleBody instanceof Steve) {
                    ((Steve) affectableCircleBody).jumpAvailable = true;
                }

                offset.setMag(maxCollisionDist);
                affectableCircleBody.POS.set(Vector2D.add(closestPoint, offset));
                double offsetAngle = offset.angle();
                Vector2D collisionForce = Vector2D.setMag(offset, -1.3 * affectableCircleBody.RELATIVE_VEL.xFromAngle(offsetAngle));
                affectableCircleBody.applyForce(collisionForce);
                if (isCollisionAffectable) {
                    collisionForce.mult(-1);
                    ((PhysicalMovementTreater) movementTreater).applyForce(collisionForce);
                    if (IS_CIRCLE_BODY) {
                        affectableCircleBody.ALREADY_AFFECTED_CIRCLE_BODIES.add(THIS_AS_CIRCLE_BODY);
                    }
                }

                spawnParticles(closestPoint, (int) affectableCircleBody.RELATIVE_VEL.mag() / 4);
            }
        }
    }

    private boolean isPhysical() {
        return (movementTreater instanceof PhysicalMovementTreater);
    }

    public abstract Vector2D closestPoint(CircleBody cb);

    private void spawnParticles(Vector2D sourcePoint, int amount) {
        Color sourcePointColor = (texture == null) ? Color.BLACK : new Color(texture.rgbAt(sourcePoint, true));
        for (int i = 0; i < amount; i++) {
            new PlatformParticle(DISPLAY, sourcePoint, sourcePointColor);
        }
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingG = mainPaintingGraphics(sourceGraphics);
        Shape mainPaintingShape = mainPaintingShape();
        if (texture != null) {
            texture.paint(mainPaintingG);
        }
        mainPaintingG.setColor(Color.BLACK);
        mainPaintingG.draw(mainPaintingShape);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.translate(POS.x, POS.y);
        mainPaintingGraphics.rotate(angle);
        mainPaintingGraphics.setStroke(new BasicStroke(3));
        return mainPaintingGraphics;
    }

    @Override
    public abstract Shape mainPaintingShape();

}

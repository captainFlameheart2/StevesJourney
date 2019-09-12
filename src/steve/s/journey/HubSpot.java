package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonat
 */
public class HubSpot implements Updatable, MouseInputProcessable, Paintable {

    private final Display DISPLAY;

    public Map map;

    public boolean followingMouse;
    private static HubSpot markedHubSpot;

    final Vector2D POS;
    private double radius;
    private static final int TARGET_RADIUS = 20;
    private final Color FILL_COLOR, STROKE_COLOR;
    private final double SHADOW_ANGLE, LIGHT_ANGLE, SHADOW_OFFSET = TARGET_RADIUS / 3, LIGHT_OFFSET = TARGET_RADIUS / 2;
    private final Vector2D SHADOW_TRANSLATION, LIGHT_TRANSLATION,
            LIGHT_SIZE = new Vector2D(TARGET_RADIUS / 2, TARGET_RADIUS), HALF_LIGHT_SIZE = Vector2D.div(LIGHT_SIZE, 2);

    private final ArrayList<HubSpot> CONNECTED_SPOTS = new ArrayList<>();

    public HubSpot(Display display, Vector2D pos, Color color, double shadowAngle) {
        DISPLAY = display;

        try {
            map = new Map(DISPLAY, "Test");
        } catch (IOException ex) {
            Logger.getLogger(HubSpot.class.getName()).log(Level.SEVERE, null, ex);
        }

        POS = pos;
        FILL_COLOR = color;
        STROKE_COLOR = Color.BLACK;
        SHADOW_ANGLE = shadowAngle;
        SHADOW_TRANSLATION = Vector2D.fromAngle(SHADOW_ANGLE, SHADOW_OFFSET);
        LIGHT_TRANSLATION = Vector2D.setMag(SHADOW_TRANSLATION, -LIGHT_OFFSET);
        LIGHT_ANGLE = LIGHT_TRANSLATION.angle();
    }

    public void addConnectedSpot(HubSpot connectedSpot) {
        CONNECTED_SPOTS.add(connectedSpot);
    }

    public HubSpot bestConnectedSpot(double desiredDirectionAngle) {
        HubSpot bestConnectedSpot = null;
        double leastDirectionAngleOffset = Double.NaN;
        for (HubSpot currentConnectedSpot : CONNECTED_SPOTS) {
            double currentConnectionAngle = Vector2D.sub(currentConnectedSpot.POS, POS).angle(true);
            double currentDirectionAngleOffset = Math.abs(desiredDirectionAngle - currentConnectionAngle);
            if (Double.isNaN(leastDirectionAngleOffset) || currentDirectionAngleOffset < leastDirectionAngleOffset) {
                bestConnectedSpot = currentConnectedSpot;
                leastDirectionAngleOffset = currentDirectionAngleOffset;
            }
        }
        return bestConnectedSpot;
    }

    @Override
    public void update() {
        if (followingMouse) {
            POS.set(DISPLAY.MOUSE_HANDLER.mousePos());
        }
        radius = StevesJourney.lerp(radius, TARGET_RADIUS, .1);
    }

    @Override
    public void processMousePress(int button) {
        if (containsMouse()) {
            if (button == MouseEvent.BUTTON1) {
                followingMouse = !followingMouse;
            } else if (button == MouseEvent.BUTTON2) {
                if (markedHubSpot == null) {
                    markedHubSpot = this;
                } else {
                    if (CONNECTED_SPOTS.contains(markedHubSpot)) {

                    } else {
                        HubPath path = new HubPath(DISPLAY, this, markedHubSpot);
                        DISPLAY.HUB_WORLD.PATHS.add(path);
                        Updatable.addMember(path, 0);
                        Paintable.addMember(path, 0);
                        markedHubSpot = null;
                    }
                }
            }
        }
    }

    private boolean containsMouse() {
        return POS.dist(DISPLAY.MOUSE_HANDLER.mousePos()) < radius;
    }

    @Override
    public void processMouseRelease(int button) {
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.circleWithinPaintDist(POS, radius, DISPLAY.CAMERA);
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingG = mainPaintingGraphics(sourceGraphics);
        Ellipse2D mainPaintingShape = (Ellipse2D) mainPaintingShape();
        shadowGraphics(mainPaintingG).fill(mainPaintingShape);  // Optimisation may be needed
        mainPaintingG.setColor(FILL_COLOR);
        mainPaintingG.fill(mainPaintingShape);
        mainPaintingG.setColor(STROKE_COLOR);
        mainPaintingG.draw(mainPaintingShape);
        Graphics2D ligthG = lightGraphics(mainPaintingG);
        Ellipse2D lightShape = lightShape();
        ligthG.setColor(Color.WHITE);
        ligthG.fill(lightShape);
        ligthG.setColor(STROKE_COLOR);
        ligthG.draw(lightShape);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.translate(POS.x, POS.y);
        mainPaintingGraphics.setStroke(new BasicStroke(3));
        return mainPaintingGraphics;
    }

    private Graphics2D shadowGraphics(Graphics2D mainPaintingGraphics) {
        Graphics2D shadowGraphics = (Graphics2D) mainPaintingGraphics.create();
        shadowGraphics.translate(SHADOW_TRANSLATION.x, SHADOW_TRANSLATION.y);
        shadowGraphics.setColor(new Color(0, 0, 0, 150));
        return shadowGraphics;
    }

    private Graphics2D lightGraphics(Graphics2D mainPaintingGraphics) {
        Graphics2D lightGraphics = (Graphics2D) mainPaintingGraphics.create();
        lightGraphics.translate(LIGHT_TRANSLATION.x, LIGHT_TRANSLATION.y);
        lightGraphics.rotate(LIGHT_ANGLE);
        return lightGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        double diameter = 2 * radius;
        return new Ellipse2D.Double(-radius, -radius, diameter, diameter);
    }

    private Ellipse2D lightShape() {
        return new Ellipse2D.Double(-HALF_LIGHT_SIZE.x, -HALF_LIGHT_SIZE.y, LIGHT_SIZE.x, LIGHT_SIZE.y);
    }

}

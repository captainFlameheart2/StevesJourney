/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steve.s.journey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jonat
 */
public class RectangleParticle extends Particle {

    private final Vector2D SIZE, HALF_SIZE;
    private double angle;
    private final double ANGLE_CHANGE;

    public RectangleParticle(Display display, double lifespanLerpVal, Vector2D pos, Vector2D vel, Vector2D size, double angleChange) {
        super(display, 255, lifespanLerpVal, pos, vel);
        SIZE = size;
        HALF_SIZE = Vector2D.div(SIZE, 2);
        ANGLE_CHANGE = angleChange;
    }

    @Override
    public void update() {
        super.update();
        angle += ANGLE_CHANGE;
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.rectWithinPaintDist(pos(), SIZE, angle, DISPLAY.CAMERA);
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).fill(mainPaintingShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = super.mainPaintingGraphics(sourceGraphics);
        mainPaintingGraphics.rotate(angle);
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return new Rectangle2D.Double(-HALF_SIZE.x, -HALF_SIZE.y, SIZE.x, SIZE.y);
    }

}

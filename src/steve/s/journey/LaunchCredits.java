package steve.s.journey;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;

/**
 *
 * @author jonat
 */
public class LaunchCredits implements Updatable, KeyInputProcessable, Paintable {

    private final Display DISPLAY;

    float lightX = -500, lightHalfLength = 300;

    private final String CREDIITS = "A Game By Jonatan Larsson";

    public LaunchCredits(Display display) {
        DISPLAY = display;
        DISPLAY.setBackground(Color.BLACK);

        Updatable.addMember(this, 0);
        KeyInputProcessable.MEMBERS.add(this);
        Paintable.addMember(this, 0);
    }

    @Override
    public void update() {
        lightX += 3;
        if (lightX > 999) {
            openStartScreen();
        }
    }

    private void openStartScreen() {
        DISPLAY.setBackground(new Color(150, 255, 255));
        Updatable.removeMember(this, 0);
        KeyInputProcessable.MEMBERS_TO_BE_REMOVED.add(this);
        Paintable.removeMember(this, 0);
        Updatable.addMember(DISPLAY.START_SCREEN, 0);
        KeyInputProcessable.MEMBERS_TO_BE_ADDED.add(DISPLAY.START_SCREEN);
        Paintable.addMember(DISPLAY.START_SCREEN, 0);
    }

    @Override
    public void processKeyPress(int keyCode) {
        openStartScreen();
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
        mainPaintingGraphics(sourceGraphics).drawString(CREDIITS, 0, 0);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = Paintable.stringGraphics(
                sourceGraphics, new Vector2D(), CREDIITS, new Font(Font.SERIF, Font.HANGING_BASELINE, 50)
        );
        float[] fractions = {0, .5f, 1};
        Color[] colors = {Color.BLACK, Color.WHITE, Color.BLACK};
        LinearGradientPaint paint = new LinearGradientPaint(lightX - lightHalfLength, 0, lightX + lightHalfLength, 0, fractions, colors);
        mainPaintingGraphics.setPaint(paint);
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return null;
    }

}

package steve.s.journey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jonat
 */
public class TransitionWall implements Updatable, Paintable {

    private final Display DISPLAY;

    public static final int ID_TO_HUB = 0, ID_TO_MAIN_MENU = 1, ID_TO_HUB_CREATION = 2, ID_TO_MAP = 3;
    private final int ID;

    private long timer = 3000000000L;

    private final String TRANSITION_TEXT;

    private double y, vy;

    public TransitionWall(Display display, int id) {
        DISPLAY = display;
        ID = id;
        switch (ID) {
            case ID_TO_HUB:
                TRANSITION_TEXT = "WELCOME TO THE HUB WORLD!";
                break;
            case ID_TO_MAIN_MENU:
                TRANSITION_TEXT = "RETURNING TO THE MAIN MENU!";
                break;
            case ID_TO_HUB_CREATION:
                TRANSITION_TEXT = "LET'S CREATE A HUB WORLD!";
                break;
            case ID_TO_MAP:
                TRANSITION_TEXT = Map.currentMap.NAME;
                break;
            default:
                TRANSITION_TEXT = "";
        }
        y = -DISPLAY.getHeight();
        DISPLAY.AUDIO_PLAYER_STORAGE.OLD_TV_AUDIO_PLAYER.playAudio();

        Updatable.addMember(this, 0);
        Paintable.addMember(this, 1);
    }

    @Override
    public void update() {
        if (timerComplete()) {
            if (!whitinPaintingDist()) {
                Updatable.removeMember(this, 0);
                Paintable.removeMember(this, 1);
            }
        } else {
            vy += .5;
            timer -= NS_PER_UPDATE;
            if (timerComplete()) {
                transition();
            }
        }
        y += vy;
        if (y > 0) {
            y = 0;
            vy *= -.3;
        }
    }

    private boolean timerComplete() {
        return timer < 0;
    }

    private void transition() {
        vy = -9;
        switch (ID) {
            case ID_TO_HUB:
                DISPLAY.HUB_WORLD.open();
                break;
            case ID_TO_MAIN_MENU:
                if (Map.currentMap == null) {
                    DISPLAY.HUB_WORLD.close();
                } else {
                    Map.currentMap.close();
                }
                DISPLAY.RENDERING_HINTS.clear();
                DISPLAY.RENDERING_HINTS.add(mainMenuTransitionRenderingHints());
                DISPLAY.MENU_SCREEN_STORAGE.HOME_MENU_SCREEN.switchTo();
                break;
            case ID_TO_HUB_CREATION:
                DISPLAY.HUB_WORLD.open();
                DISPLAY.MENU_SCREEN_STORAGE.HUB_CREATION_MENU_SCREEN.switchTo();
                break;
            case ID_TO_MAP:
                DISPLAY.HUB_WORLD.close();
                Map.currentMap.open();
                break;
        }
    }

    public static RenderingHints mainMenuTransitionRenderingHints() {
        return new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    @Override
    public boolean whitinPaintingDist() {
        return y > -DISPLAY.getHeight();
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = mainPaintingGraphics(sourceGraphics);
        int ligthness = (int) StevesJourney.random(15);
        mainPaintingGraphics.setColor(new Color(ligthness, ligthness, ligthness));
        mainPaintingGraphics.fill(mainPaintingShape());

        transitionTextGraphics(mainPaintingGraphics).drawString(TRANSITION_TEXT, 0, 0);
        filterLineGraphics(mainPaintingGraphics).draw(filterLineShape());
        filterPointGraphics(mainPaintingGraphics).fill(filterPointShape());
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
        mainPaintingGraphics.translate(DISPLAY.CAMERA.POS.x, DISPLAY.CAMERA.POS.y + y);
        return mainPaintingGraphics;
    }

    private Graphics2D transitionTextGraphics(Graphics2D mainPaintingGraphics) {
        Graphics2D transitionTextGraphics = Paintable.stringGraphics(
                mainPaintingGraphics, new Vector2D(), TRANSITION_TEXT, new Font(Font.DIALOG_INPUT, Font.ITALIC, 50)
        );
        transitionTextGraphics.setColor(Color.WHITE);
        return transitionTextGraphics;
    }

    private Graphics2D filterLineGraphics(Graphics2D mainPaintingGraphics) {
        Graphics2D filterLineGraphics = (Graphics2D) mainPaintingGraphics.create();
        int halfDisplayWidth = DISPLAY.getWidth() / 2;
        filterLineGraphics.translate(StevesJourney.random(-halfDisplayWidth, halfDisplayWidth), 0);
        float[] dash = new float[15];
        float minDashAddition = 0;
        for (int i = 0; i < dash.length; i++) {
            dash[i] = minDashAddition + (float) StevesJourney.random(2);
            minDashAddition = dash[i];
        }
        filterLineGraphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 50, dash, 0));
        filterLineGraphics.setColor(new Color(255, 255, 255, (int) StevesJourney.random(50, 256)));
        return filterLineGraphics;
    }

    private Graphics2D filterPointGraphics(Graphics2D mainPaintingGraphics) {
        Graphics2D filterPointGraphics = (Graphics2D) mainPaintingGraphics.create();
        Vector2D displayHalfSize = DISPLAY.halfSize();
        filterPointGraphics.translate(StevesJourney.random(-displayHalfSize.x, displayHalfSize.x), StevesJourney.random(-displayHalfSize.y, displayHalfSize.y));
        filterPointGraphics.setColor(new Color(255, 255, 255, (int) StevesJourney.random(50, 256)));
        return filterPointGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        Vector2D displayHalfSize = DISPLAY.halfSize();
        return new Rectangle2D.Double(-displayHalfSize.x, -displayHalfSize.y, DISPLAY.getWidth(), DISPLAY.getHeight());
    }

    private Line2D filterLineShape() {
        int displayHalfHeight = DISPLAY.getHeight() / 2;
        return new Line2D.Double(0, -displayHalfHeight, 0, displayHalfHeight);
    }

    private Ellipse2D filterPointShape() {
        return new Ellipse2D.Double(0, 0, StevesJourney.random(15), StevesJourney.random(15));
    }

}

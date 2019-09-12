package steve.s.journey;

import com.sun.glass.events.KeyEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jonat
 */
public class Button implements Updatable, KeyInputProcessable, MouseInputProcessable, Paintable {

    private final Display DISPLAY;

    public static final int ID_PLAY = 0, ID_CREATE = 1, ID_QUIT = 2, ID_SETTINGS = 3,
            ID_HOME = 4,
            ID_CONTINUE = 5, ID_MAIN_MENU = 6,
            ID_MAP = 7, ID_HUB_WORLD = 8,
            ID_FROM_SCRATCH = 9, ID_FROM_OTHER_HUB_WORLD = 10, ID_BACK = 11,
            ID_SPOT = 12;
    private final int ID;
    private final String TITLE;

    private int state;
    public static final int STATE_DEFAULT = 0, STATE_HOVERED_OVER = 1, STATE_HELD_DOWN = 2, STATE_SELECTED = 3;
    public boolean selectable, selectionStatePreliminary = true;
    private final int SELECTION_KEY_CODE;
    private static final int NO_SELECTION_KEY_CODE = -1;

    private final Vector2D POS = new Vector2D();
    public final Vector2D SIZE = new Vector2D(), TARGET_SIZE = new Vector2D(), DEFAULT_SIZE = new Vector2D(), MAX_SIZE;

    private Color color;

    public Button(Display display, int id) {
        DISPLAY = display;
        ID = id;

        Vector2D displayHalfSize = DISPLAY.halfSize();

        switch (ID) {
            case ID_PLAY:
                TITLE = "PLAY";
                POS.set(0, -200);
                DEFAULT_SIZE.set(300, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_ENTER;
                break;
            case ID_CREATE:
                TITLE = "CREATE";
                DEFAULT_SIZE.set(350, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_C;
                break;
            case ID_QUIT:
                TITLE = "QUIT";
                POS.set(0, 200);
                DEFAULT_SIZE.set(200, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_Q;
                break;
            case ID_SETTINGS:
                TITLE = "SETTINGS";
                POS.set(700, 400);
                DEFAULT_SIZE.set(200, 70);
                SELECTION_KEY_CODE = KeyEvent.VK_S;
                break;
            case ID_HOME:
                TITLE = "HOME";
                POS.set(-700, 400);
                DEFAULT_SIZE.set(200, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_BACKSPACE;
                break;
            case ID_CONTINUE:
                TITLE = "CONTINUE";
                POS.set(0, -200);
                DEFAULT_SIZE.set(350, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_ENTER;
                break;
            case ID_MAIN_MENU:
                TITLE = "RETURN TO MAIN MENU";
                POS.set(0, 200);
                DEFAULT_SIZE.set(500, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_BACKSPACE;
                break;
            case ID_MAP:
                TITLE = "MAP";
                POS.set(0, -100);
                DEFAULT_SIZE.set(200, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_M;
                break;
            case ID_HUB_WORLD:
                TITLE = "HUB WORLD";
                POS.set(0, 100);
                DEFAULT_SIZE.set(300, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_H;
                break;
            case ID_FROM_SCRATCH:
                TITLE = "FROM SCRATCH";
                POS.set(0, -100);
                DEFAULT_SIZE.set(300, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_S;
                break;
            case ID_FROM_OTHER_HUB_WORLD:
                TITLE = "FROM OTHER HUB WORLD";
                POS.set(0, 100);
                DEFAULT_SIZE.set(500, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_O;
                break;
            case ID_SPOT:
                TITLE = "SPOT";
                POS.set(700, -400);
                DEFAULT_SIZE.set(200, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_S;
                break;
            case ID_BACK:
                TITLE = "BACK";
                POS.set(-700, 400);
                DEFAULT_SIZE.set(200, 100);
                SELECTION_KEY_CODE = KeyEvent.VK_BACKSPACE;
                break;
            default:
                TITLE = "";
                SELECTION_KEY_CODE = NO_SELECTION_KEY_CODE;
        }
        MAX_SIZE = Vector2D.mult(DEFAULT_SIZE, 1.2);
    }

    public void open() {
//        Updatable.MEMBERS_TO_BE_ADDED.add(this);
        Updatable.addMember(this, 0);
        KeyInputProcessable.MEMBERS_TO_BE_ADDED.add(this);
        MouseInputProcessable.MEMBERS_TO_BE_ADDED.add(this);
        Paintable.addMember(this, 0);

        TARGET_SIZE.set(DEFAULT_SIZE);
        selectable = true;
    }

    public void close() {
        TARGET_SIZE.set();
    }

    private Vector2D halfSize() {
        return Vector2D.div(SIZE, 2);
    }

    @Override
    public void processMousePress(int button) {
        if (selectable && containsMouse()) {
            assignState(STATE_HELD_DOWN);
        }
    }

    @Override
    public void processMouseRelease(int button) {
        if (selectable && state == STATE_HELD_DOWN) {
            assignState(STATE_SELECTED);
        }
    }

    @Override
    public void processKeyPress(int keyCode) {
        if (selectable && keyCode == SELECTION_KEY_CODE) {
            assignState(STATE_SELECTED);
        }
    }

    @Override
    public void processKeyRelease(int keyCode) {
    }

    private boolean containsMouse() {
        Vector2D relativeMousePos = relativeMousePos();
        Vector2D halfSize = halfSize();
        return relativeMousePos.x < halfSize.x && relativeMousePos.y < halfSize.y
                && relativeMousePos.x > -halfSize.x && relativeMousePos.y > -halfSize.y;
    }

    private Vector2D relativeMousePos() {
        return Vector2D.sub(DISPLAY.MOUSE_HANDLER.mousePos(), POS);
    }

    @Override
    public void update() {
        SIZE.lerp(TARGET_SIZE, .2);
        if (selectable) {
            if (containsMouse()) {
                DISPLAY.MOUSE_HANDLER.cursor = new Cursor(Cursor.HAND_CURSOR);
                if (state == STATE_DEFAULT) {
                    assignState(STATE_HOVERED_OVER);
                }
            } else if (state != STATE_SELECTED) {
                assignState(STATE_DEFAULT);
            }
        }
    }

    public void assignState(int newState, boolean targetSizeAlteration) {
        state = newState;
        switch (state) {
            case STATE_DEFAULT:
                if (targetSizeAlteration) {
                    TARGET_SIZE.set(DEFAULT_SIZE);
                }
                color = Color.WHITE;
                break;
            case STATE_HOVERED_OVER:
                DISPLAY.AUDIO_PLAYER_STORAGE.HOVERING_AUDIO_PLAYER.playAudio();
                if (targetSizeAlteration) {
                    TARGET_SIZE.set(MAX_SIZE);
                }
                color = Color.GRAY;
                break;
            case STATE_HELD_DOWN:
                color = Color.DARK_GRAY;
                break;
            case STATE_SELECTED: {
                DISPLAY.AUDIO_PLAYER_STORAGE.SELECTION_AUDIO_PLAYER.playAudio();
            }
            switch (ID) {
                case ID_PLAY:
                    MenuScreen.closeCurrentMenuScreen();
                    new TransitionWall(DISPLAY, TransitionWall.ID_TO_HUB);
                    break;
                case ID_CREATE:
                    DISPLAY.MENU_SCREEN_STORAGE.CREATION_MENU_SCREEN.switchTo();
                    break;
                case ID_QUIT:
                    StevesJourney.quitProcedure();
                    break;
                case ID_HOME:
                    DISPLAY.MENU_SCREEN_STORAGE.HOME_MENU_SCREEN.switchTo();
                    break;
                case ID_CONTINUE:
                    DISPLAY.MENU_SCREEN_STORAGE.PAUSE_MENU_SCREEN.close();
                    break;
                case ID_MAIN_MENU:
                    MenuScreen.closeCurrentMenuScreen();
                    new TransitionWall(DISPLAY, TransitionWall.ID_TO_MAIN_MENU);
                    break;
                case ID_HUB_WORLD:
                    DISPLAY.MENU_SCREEN_STORAGE.HUB_CREATION_SPECIFICATION_MENU_SCREEN.switchTo();
                    break;
                case ID_FROM_SCRATCH:
                    MenuScreen.closeCurrentMenuScreen();
                    new TransitionWall(DISPLAY, TransitionWall.ID_TO_HUB_CREATION);
                    break;
                case ID_SPOT:
                    HubSpot spot = new HubSpot(DISPLAY, new Vector2D(), Color.RED, 0);
                    spot.followingMouse = true;
                    DISPLAY.HUB_WORLD.SPOTS.add(spot);
//                    Updatable.MEMBERS_TO_BE_ADDED.add(spot);
                    Updatable.addMember(spot, 0);
                    MouseInputProcessable.MEMBERS_TO_BE_ADDED.add(spot);
                    Paintable.addMember(spot, 0);
                case ID_BACK:
                    MenuScreen.goBack();
                    break;
                case ID_SETTINGS:
                    DISPLAY.MENU_SCREEN_STORAGE.SETTINGS_MENU_SCREEN.switchTo();
                    break;
            }
            color = Color.GREEN;
            break;
        }
    }

    public void assignState(int newState) {
        assignState(newState, true);
    }

    public int getState() {
        return state;
    }

    public boolean selectionStatePreliminary() {
        return selectionStatePreliminary;
    }

    @Override
    public boolean whitinPaintingDist() {
        return true;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingG = mainPaintingGraphics(sourceGraphics);
        Rectangle2D mainPaintingShape = (Rectangle2D) mainPaintingShape();

        shadowGraphics(mainPaintingG).fill(mainPaintingShape);

        mainPaintingG.setColor(color);
        mainPaintingG.fill(mainPaintingShape);
        mainPaintingG.setColor(Color.BLACK);
        mainPaintingG.draw(mainPaintingShape);

        titleGraphics(mainPaintingG).drawString(TITLE, 0, 0);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D paintingGraphics = (Graphics2D) sourceGraphics.create();
        double scaling = 1 / DISPLAY.CAMERA.getScaling();
        paintingGraphics.scale(scaling, scaling);
        paintingGraphics.translate(DISPLAY.CAMERA.POS.x + POS.x, DISPLAY.CAMERA.POS.y + POS.y);
        paintingGraphics.setStroke(new BasicStroke(3));
        return paintingGraphics;
    }

    private Graphics2D shadowGraphics(Graphics2D mainPaintingGraphics) {
        Graphics2D shadowGraphics = (Graphics2D) mainPaintingGraphics.create();
        Vector2D shadowOffset = Vector2D.mult(relativeMousePos(), -.1);
        shadowGraphics.translate(shadowOffset.x, shadowOffset.y);
        shadowGraphics.setColor(new Color(0, 0, 0, 50));
        return shadowGraphics;
    }

    private Graphics2D titleGraphics(Graphics2D mainPaintingGraphics) {
        Graphics2D titleGraphics = (Graphics2D) mainPaintingGraphics.create();
        titleGraphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int) SIZE.y / 3));
        FontMetrics fm = titleGraphics.getFontMetrics();
        titleGraphics.translate(-fm.stringWidth(TITLE) / 2, fm.getAscent() / 2);
        return titleGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        Vector2D halfSize = halfSize();
        return new Rectangle2D.Double(-halfSize.x, -halfSize.y, SIZE.x, SIZE.y);
    }

}

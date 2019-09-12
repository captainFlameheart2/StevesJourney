package steve.s.journey;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;

/**
 *
 * @author jonat
 */
public class MenuScreenQuestion implements Updatable, KeyInputProcessable, MouseInputProcessable, Paintable {

    private final Display DISPLAY;

    private static final int STATE_ASKING = 0, STATE_DEFAULT = 1, STATE_CLOSING = 2;
    private int state = STATE_DEFAULT;

    private final String QUESTION_STRING;
    private String visualString = "";
    private int size;

    private final Vector2D POS;

    public MenuScreenQuestion(Display display, String questionString, Vector2D pos) {
        DISPLAY = display;
        QUESTION_STRING = questionString;
        POS = pos;
    }

    @Override
    public void update() {
        if (state == STATE_ASKING && DISPLAY.getUpdateCount() % 9 == 0) {
            addVisualChar();
            if (visualString.length() == QUESTION_STRING.length()) {
                state = STATE_DEFAULT;
            }
        } else if (state == STATE_CLOSING) {
            size--;
            if (size <= 0) {
                finaliseClosure();
            }
        }
    }

    private void addVisualChar() {
        char c = QUESTION_STRING.charAt(visualString.length());
        if (c != ' ') {
            DISPLAY.AUDIO_PLAYER_STORAGE.TYPE_WRITING_AUDIO_PLAYER.playAudio();
        }
        visualString += c;
    }

    public boolean isDoneAsking() {
        return state != STATE_ASKING;
    }

    public void ask() {
//        Updatable.MEMBERS_TO_BE_ADDED.add(this);
        Updatable.addMember(this, 0);
        KeyInputProcessable.MEMBERS_TO_BE_ADDED.add(this);
        MouseInputProcessable.MEMBERS_TO_BE_ADDED.add(this);
        Paintable.addMember(this, 0);
        size = 35;
        state = STATE_ASKING;
    }

    public void close() {
        state = STATE_CLOSING;
    }

    private void finaliseClosure() {
//        Updatable.MEMBERS_TO_BE_REMOVED.add(this);
        Updatable.removeMember(this, 0);
        KeyInputProcessable.MEMBERS_TO_BE_REMOVED.add(this);
        MouseInputProcessable.MEMBERS_TO_BE_REMOVED.add(this);
        Paintable.removeMember(this, 0);
        visualString = "";
    }

    @Override
    public void processKeyPress(int keyCode) {
        skipAskingState();
    }

    @Override
    public void processMousePress(int button) {
        skipAskingState();
    }

    private void skipAskingState() {
        if (state == STATE_ASKING) {
            visualString = QUESTION_STRING;
            state = STATE_DEFAULT;
        }
    }

    @Override
    public void processKeyRelease(int keyCode) {
    }

    @Override
    public void processMouseRelease(int button) {
    }

    @Override
    public boolean whitinPaintingDist() {
        return true;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).drawString(visualString, 0, 0);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        Graphics2D mainPaintingGraphics = Paintable.stringGraphics(
                sourceGraphics, POS, visualString, new Font(Font.DIALOG_INPUT, Font.BOLD, size)
        );
        mainPaintingGraphics.setColor(Color.BLACK);
        return mainPaintingGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return null;
    }

}

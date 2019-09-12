package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class MenuScreen implements Updatable {

    private final Display DISPLAY;

    private static final int STATE_OPENING = 0, STATE_DEFAULT = 1, STATE_CLOSING = 2;
    private int state = STATE_DEFAULT;
    private int currentTransitionButtonIndex;

    private static MenuScreen currentMenuScreen, previousMenuScreen;
    private boolean toBeOpened;

    private final ArrayList<Button> BUTTONS;
    private final Button FIRST_BUTTON;
    private final int BUTTON_AMOUNT;
    private final MenuScreenQuestion QUESTION;

    public MenuScreen(Display display, ArrayList<Button> buttons, MenuScreenQuestion question) {
        DISPLAY = display;
        BUTTONS = buttons;
        BUTTON_AMOUNT = BUTTONS.size();
        FIRST_BUTTON = (BUTTON_AMOUNT > 0) ? BUTTONS.get(0) : null;
        QUESTION = question;
//        Updatable.MEMBERS.add(this);
Updatable.addMember(this, 0);
    }

    public void switchTo() {
        if (currentMenuScreen == null) {
            open();
        } else {
            currentMenuScreen.close();
            toBeOpened = true;
        }
    }

    private void open() {
        toBeOpened = false;
        currentMenuScreen = this;
        if (QUESTION != null) {
            QUESTION.ask();
        }
        state = STATE_OPENING;
    }

    public static void goBack() {
        previousMenuScreen.switchTo();
    }

    public boolean isCurrent() {
        return this == currentMenuScreen;
    }

    public void close() {
        for (Button b : BUTTONS) {
            b.selectable = false;
        }
        if (QUESTION != null) {
            QUESTION.close();
        }
        state = STATE_CLOSING;
    }
    
    public static void closeCurrentMenuScreen() {
        currentMenuScreen.close();
    }

    private void finaliseClosure() {
        removeElementsFromScene();
        for (Button b : BUTTONS) {
            if (b.selectionStatePreliminary()) {
                b.assignState(Button.STATE_DEFAULT, false);
            }
        }
        state = STATE_DEFAULT;
        currentMenuScreen = null;
        previousMenuScreen = this;
    }

    private boolean closureTransitionComplete() {
        return state == STATE_CLOSING && (FIRST_BUTTON == null || FIRST_BUTTON.SIZE.mag() < 5);
    }

    @Override
    public void update() {
        if (state != STATE_DEFAULT) {
            transition();
        }
        if (closureTransitionComplete()) {
            finaliseClosure();
        }
        if (toBeOpened && currentMenuScreen == null) {
            open();
        }
    }

    private void transition() {
        if (BUTTON_AMOUNT > 0 && DISPLAY.getUpdateCount() % 15 == 0 && (QUESTION == null || QUESTION.isDoneAsking())) {
            if (state == STATE_OPENING) {
                currentTransitionButton().open();
                if (currentTransitionButtonIndex == BUTTON_AMOUNT - 1) {
                    state = STATE_DEFAULT;
                } else {
                    currentTransitionButtonIndex++;
                }
            } else if (state == STATE_CLOSING) {
                currentTransitionButton().close();
                if (currentTransitionButtonIndex != 0) {
                    currentTransitionButtonIndex--;
                }
            }
        }
    }

    private Button currentTransitionButton() {
        return BUTTONS.get(currentTransitionButtonIndex);
    }

    public void removeElementsFromScene() {
//        Updatable.MEMBERS_TO_BE_REMOVED.addAll(BUTTONS);
Updatable.removeMembers(BUTTONS, 0);
        KeyInputProcessable.MEMBERS_TO_BE_REMOVED.addAll(BUTTONS);
        MouseInputProcessable.MEMBERS_TO_BE_REMOVED.addAll(BUTTONS);
        Paintable.removeMembers(BUTTONS, 0);

        if (QUESTION != null) {
//            Updatable.MEMBERS_TO_BE_REMOVED.add(QUESTION);
Updatable.removeMember(QUESTION, 0);
            Paintable.removeMember(QUESTION, 0);
        }
    }

}

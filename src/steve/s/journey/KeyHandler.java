package steve.s.journey;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author jonat
 */
public class KeyHandler extends KeyAdapter {

    private final Display DISPLAY;

    private static final boolean[] KEYS_HELD_DOWN = new boolean[9999];

    public KeyHandler(Display display) {
        DISPLAY = display;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        KEYS_HELD_DOWN[keyCode] = true;
        KeyInputProcessable.modifyMembers();
        KeyInputProcessable.processKeyPressForAll(keyCode);

        if (keyCode == KeyEvent.VK_ESCAPE && Display.pausable) {
            MenuScreen pauseMenuScreen = DISPLAY.MENU_SCREEN_STORAGE.PAUSE_MENU_SCREEN;
            if (!pauseMenuScreen.isCurrent()) {
                pauseMenuScreen.switchTo();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        KEYS_HELD_DOWN[keyCode] = false;
        KeyInputProcessable.modifyMembers();
        KeyInputProcessable.processKeyReleaseForAll(keyCode);
    }
    
    public static boolean keyHeldDown(int keyCode) {
        return KEYS_HELD_DOWN[keyCode];
    }

}

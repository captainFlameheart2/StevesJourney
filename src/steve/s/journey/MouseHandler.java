package steve.s.journey;

import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author jonat
 */
public class MouseHandler extends MouseAdapter {

    private final Display DISPLAY;
    
    public Cursor cursor = Cursor.getDefaultCursor();
    
    private static final boolean[] HELD_DOWN_MOUSE_BUTTONS = new boolean[MouseInfo.getNumberOfButtons()];

    public MouseHandler(Display display) {
        DISPLAY = display;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        HELD_DOWN_MOUSE_BUTTONS[button] = true;
        MouseInputProcessable.modifyMembers();
        MouseInputProcessable.processMousePressForAll(button);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        HELD_DOWN_MOUSE_BUTTONS[button] = false;
        MouseInputProcessable.modifyMembers();
        MouseInputProcessable.processMouseReleaseForAll(button);
    }

    public Vector2D mousePos() {
        Vector2D mousePos = new Vector2D(MouseInfo.getPointerInfo().getLocation());
        mousePos.sub(DISPLAY.halfSize());
        return mousePos;
    }

}

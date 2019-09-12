package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public interface MouseInputProcessable {

    public static final ArrayList<MouseInputProcessable> MEMBERS = new ArrayList<>(),
            MEMBERS_TO_BE_ADDED = new ArrayList<>(), MEMBERS_TO_BE_REMOVED = new ArrayList<>();

    public static void modifyMembers() {
        MEMBERS.addAll(MEMBERS_TO_BE_ADDED);
        MEMBERS_TO_BE_ADDED.clear();
        MEMBERS.removeAll(MEMBERS_TO_BE_REMOVED);
        MEMBERS_TO_BE_REMOVED.clear();
    }

    public static void processMousePressForAll(int mousebutton) {
        for (MouseInputProcessable member : MEMBERS) {
            member.processMousePress(mousebutton);
        }
    }

    public static void processMouseReleaseForAll(int mousebutton) {
        for (MouseInputProcessable member : MEMBERS) {
            member.processMouseRelease(mousebutton);
        }
    }

    public void processMousePress(int mousebutton);

    public void processMouseRelease(int mousebutton);

}

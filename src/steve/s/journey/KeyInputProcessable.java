/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public interface KeyInputProcessable {

    public static final ArrayList<KeyInputProcessable> MEMBERS = new ArrayList<>(),
            MEMBERS_TO_BE_ADDED = new ArrayList<>(), MEMBERS_TO_BE_REMOVED = new ArrayList<>();

    public static void modifyMembers() {
        MEMBERS.addAll(MEMBERS_TO_BE_ADDED);
        MEMBERS_TO_BE_ADDED.clear();
        MEMBERS.removeAll(MEMBERS_TO_BE_REMOVED);
        MEMBERS_TO_BE_REMOVED.clear();
    }

    public static void processKeyPressForAll(int keyCode) {
        for (KeyInputProcessable member : MEMBERS) {
            member.processKeyPress(keyCode);
        }
    }

    public static void processKeyReleaseForAll(int keyCode) {
        for (KeyInputProcessable member : MEMBERS) {
            member.processKeyRelease(keyCode);
        }
    }

    public void processKeyPress(int keyCode);

    public void processKeyRelease(int keyCode);
}

package steve.s.journey;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public interface Paintable {

    public static final ArrayList<ArrayList<Paintable>> MEMBER_LISTS = new ArrayList<>(),
            MEMBERS_TO_BE_ADDED_LISTS = new ArrayList<>(), MEMBERS_TO_BE_REMOVED_LISTS = new ArrayList<>();

    public static void modifyMemberLists() {
        for (int i = 0; i < MEMBER_LISTS.size(); i++) {
            ArrayList<Paintable> members = MEMBER_LISTS.get(i),
                    membersToBeAdded = MEMBERS_TO_BE_ADDED_LISTS.get(i),
                    membersToBeRemoved = MEMBERS_TO_BE_REMOVED_LISTS.get(i);

            members.addAll(membersToBeAdded);
            membersToBeAdded.clear();

            members.removeAll(membersToBeRemoved);
            membersToBeRemoved.clear();
        }
    }

    public static void paintAllWhitingPaintingDist(Graphics2D sourceGraphics) {
        for (ArrayList<Paintable> members : MEMBER_LISTS) {
            for (Paintable member : members) {
                if (member.whitinPaintingDist()) {
                    member.paint(sourceGraphics);
                }
            }
        }
    }

    public static void addMembers(ArrayList members, int paintingOrder) {
        int memberListAmount = MEMBER_LISTS.size();
        if (paintingOrder >= memberListAmount) {
            for (int i = memberListAmount; i <= paintingOrder; i++) {
                MEMBER_LISTS.add(new ArrayList<>());
                MEMBERS_TO_BE_ADDED_LISTS.add(new ArrayList<>());
                MEMBERS_TO_BE_REMOVED_LISTS.add(new ArrayList<>());
            }
        }
        MEMBERS_TO_BE_ADDED_LISTS.get(paintingOrder).addAll(members);
    }

    public static void addMember(Paintable member, int paintingOrder) {
        ArrayList<Paintable> addingMembers = new ArrayList<>();
        addingMembers.add(member);
        addMembers(addingMembers, paintingOrder);
    }

    public static void removeMembers(ArrayList members, int paintingOrder) {
        MEMBERS_TO_BE_REMOVED_LISTS.get(paintingOrder).addAll(members);
    }

    public static void removeMember(Paintable member, int paintingOrder) {
        ArrayList<Paintable> removingMembers = new ArrayList<>();
        removingMembers.add(member);
        removeMembers(removingMembers, paintingOrder);
    }

    public boolean whitinPaintingDist();

    public static Vector2D closestRectPointToOtherPoint(Vector2D rectPos, Vector2D rectSize, double angle, Vector2D otherPoint) {
        Vector2D closestPoint = Vector2D.sub(otherPoint, rectPos);
        closestPoint.rotate(-angle);
        Vector2D rectHalfSize = Vector2D.div(rectSize, 2);
        closestPoint.limitComponents(-rectHalfSize.x, rectHalfSize.x, -rectHalfSize.y, rectHalfSize.y);
        closestPoint.rotate(angle);
        closestPoint.add(rectPos);
        return closestPoint;
    }

    public static boolean rectWithinPaintDist(Vector2D rectPos, Vector2D rectSize, double angle, Camera camera) {//Display display) {
//        Vector2D cameraPos = display.CAMERA.POS;
        return closestRectPointToOtherPoint(rectPos, rectSize, angle, camera.POS).dist(camera.POS) < camera.paintDist();
    }

    public static boolean circleWithinPaintDist(Vector2D circlePos, double circleRadius, Camera camera) {
        return circlePos.dist(camera.POS) - circleRadius < camera.paintDist();
    }

    public void paint(Graphics2D sourceGraphics);

    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics);

    public static Graphics2D stringGraphics(Graphics2D sourceGraphics, Vector2D pos, String s, Font f) {
        Graphics2D stringGraphics = (Graphics2D) sourceGraphics.create();
        stringGraphics.setFont(f);
        FontMetrics fm = stringGraphics.getFontMetrics();
        stringGraphics.translate(pos.x - fm.stringWidth(s) / 2, pos.y + fm.getAscent() / 2);
        return stringGraphics;
    }

    public Shape mainPaintingShape();

}

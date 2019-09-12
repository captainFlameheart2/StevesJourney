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
public interface Updatable {

    public static final long NS_PER_UPDATE = 1000000000 / 90;

    public static final ArrayList<ArrayList<Updatable>> MEMBER_LISTS = new ArrayList<>(),
            MEMBER_LISTS_TO_BE_ADDED = new ArrayList<>(),
            MEMBERS_TO_BE_ADDED_LISTS = new ArrayList<>(), MEMBERS_TO_BE_REMOVED_LISTS = new ArrayList<>();
//    public static int memberListAmount = 0;

    public static void modifyMemberLists() {
        MEMBER_LISTS.addAll(MEMBER_LISTS_TO_BE_ADDED);
        MEMBER_LISTS_TO_BE_ADDED.clear();
        for (int i = 0; i < MEMBERS_TO_BE_ADDED_LISTS.size(); i++) {
            ArrayList<Updatable> members = MEMBER_LISTS.get(i),
                    membersToBeAdded = MEMBERS_TO_BE_ADDED_LISTS.get(i),
                    membersToBeRemoved = MEMBERS_TO_BE_REMOVED_LISTS.get(i);

            members.addAll(membersToBeAdded);
            membersToBeAdded.clear();

            members.removeAll(membersToBeRemoved);
            membersToBeRemoved.clear();
        }
    }

    public static void updateAll() {
        for (ArrayList<Updatable> members : MEMBER_LISTS) {
            for (Updatable member : members) {
                member.update();
            }
        }
    }

    public static void addMembers(ArrayList members, int updatingOrder) {
        int memberListAmount = MEMBER_LISTS.size();
        if (updatingOrder >= memberListAmount) {
            for (int i = memberListAmount; i <= updatingOrder; i++) {
//                MEMBER_LISTS.add(new ArrayList<>());
                MEMBER_LISTS_TO_BE_ADDED.add(new ArrayList<>());
                MEMBERS_TO_BE_ADDED_LISTS.add(new ArrayList<>());
                MEMBERS_TO_BE_REMOVED_LISTS.add(new ArrayList<>());
            }
        }
        MEMBERS_TO_BE_ADDED_LISTS.get(updatingOrder).addAll(members);
    }

    public static void addMember(Updatable member, int updatingOrder) {
        ArrayList<Updatable> addingMembers = new ArrayList<>();
        addingMembers.add(member);
        addMembers(addingMembers, updatingOrder);
    }

    public static void removeMembers(ArrayList members, int updatingOrder) {
        MEMBERS_TO_BE_REMOVED_LISTS.get(updatingOrder).addAll(members);
    }

    public static void removeMember(Updatable member, int updatingOrder) {
        ArrayList<Updatable> removingMembers = new ArrayList<>();
        removingMembers.add(member);
        removeMembers(removingMembers, updatingOrder);
    }

//    public static final ArrayList<Updatable> MEMBERS = new ArrayList<>(),
//            MEMBERS_TO_BE_ADDED = new ArrayList<>(), MEMBERS_TO_BE_REMOVED = new ArrayList<>();
//    public static final ArrayList<Updatable> SPECIAL_MEMBERS = new ArrayList<>(),
//            SPECIAL_MEMBERS_TO_BE_ADDED = new ArrayList<>(), SPECIAL_MEMBERS_TO_BE_REMOVED = new ArrayList<>();
//
//    public static void modifyMembers() {
//        MEMBERS.addAll(MEMBERS_TO_BE_ADDED);
//        MEMBERS_TO_BE_ADDED.clear();
//        MEMBERS.removeAll(MEMBERS_TO_BE_REMOVED);
//        MEMBERS_TO_BE_REMOVED.clear();
//       
//        SPECIAL_MEMBERS.addAll(SPECIAL_MEMBERS_TO_BE_ADDED);
//        SPECIAL_MEMBERS_TO_BE_ADDED.clear();
//        SPECIAL_MEMBERS.removeAll(SPECIAL_MEMBERS_TO_BE_REMOVED);
//        SPECIAL_MEMBERS_TO_BE_REMOVED.clear();
//    }
//
//    public static void updateAll() {
//        for (Updatable member : MEMBERS) {
//            member.update();
//        }
//        for (Updatable specialMember : SPECIAL_MEMBERS) {
//            specialMember.update();
//        }
//    }
    public void update();

}

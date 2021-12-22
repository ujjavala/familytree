package com.family.tree.util;

import com.family.tree.enums.Gender;
import com.family.tree.enums.Relationship;
import com.family.tree.model.Member;

import java.util.*;
import java.util.function.Predicate;

import static com.family.tree.enums.Gender.FEMALE;
import static com.family.tree.util.StreamUtil.getEntryStream;
import static java.util.stream.Collectors.toList;

public class MemberUtil {

    public static boolean isGenderMatchWithParent(Member member, Gender parentGender) {
        return member.getParent().getGender().equals(parentGender);
    }

    public static boolean isBloodline(String name, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return getEntryStream(lookup).anyMatch(memberEntry -> memberEntry.getKey().containsKey(name) && memberEntry.getValue().getName().equals(name));
    }

    public static boolean isInvalidRelationship(String relationship) {
        return Arrays.stream(Relationship.values()).noneMatch(relationship1 -> relationship1.relationValue.equals(relationship));
    }

    public static boolean isParentNotFemale(String parentName, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return getEntryStream(lookup).noneMatch(member -> member.getKey().containsKey(parentName)
                && member.getKey().containsValue(FEMALE));
    }

    public static boolean isParentNotPresent(String parentName, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return getEntryStream(lookup).noneMatch(member -> member.getKey().containsKey(parentName));
    }

    public static List<String> getSiblingRelationShips(Member member, boolean isBloodMember) {
        List<String> siblings = new ArrayList<>();
        if (!isBloodMember) return siblings;
        List<Member> siblingList = new ArrayList<>(member.getParent().getChildren());
        siblingList.remove(member);
        siblings = siblingList.stream().map(Member::getName).collect(toList());
        return siblings;
    }

    public static Predicate<String> getRelativeMatch(Gender relationGender, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return relative ->
                lookup.keySet().stream().anyMatch(currentRelative ->
                        currentRelative.containsKey(relative) && currentRelative.containsValue(relationGender));
    }

    public static Optional<Map.Entry<Map<String, Gender>, Member>> getMembers(String name, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return getEntryStream(lookup).filter(memberEntry -> memberEntry.getKey().containsKey(name))
                .findFirst();
    }

}

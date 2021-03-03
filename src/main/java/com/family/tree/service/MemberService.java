package com.family.tree.service;

import com.family.tree.enums.Gender;
import com.family.tree.enums.Message;
import com.family.tree.model.Member;

import java.util.*;
import java.util.stream.Collectors;

import static com.family.tree.enums.Gender.*;
import static com.family.tree.enums.Gender.Female;
import static com.family.tree.enums.Message.*;
import static com.family.tree.misc.Constants.*;
import static java.util.stream.Collectors.*;

public class MemberService {
    private final LinkedHashMap<Map<String, Gender>, Member> lookup;

    public MemberService(LinkedHashMap<Map<String, Gender>, Member> lookup) {
        this.lookup = lookup;
    }

    public Message addChild(String parentName, String name, String gender) {
        if (lookup.entrySet().stream().noneMatch(member -> member.getKey().containsKey(parentName)))
            return PERSON_NOT_FOUND;
        if (lookup.entrySet().stream().noneMatch(member -> member.getKey().containsKey(parentName)
                && member.getKey().containsValue(Female)))
            return CHILD_ADDITION_FAILED;
        lookup.get(Map.of(parentName, Female)).addChild(new Member(name, Gender.valueOf(gender), Optional.empty()));
        return CHILD_ADDED;
    }

    public Object getRelationship(String name, String relationship) {
        if (getMembers(name).isPresent()) {
            Member member = getMembers(name).get().getValue();
            switch (relationship) {
                case SIBLINGS:
                    return getSiblingRelationShips(member, isBloodline(name));
                case MATERNAL_AUNT:
                    return getExtendedRelationShips(member, Female, Female, isBloodline(name));
                case MATERNAL_UNCLE:
                    return getExtendedRelationShips(member, Female, Male, isBloodline(name));
                case PATERNAL_AUNT:
                    return getExtendedRelationShips(member, Male, Female, isBloodline(name));
                case PATERNAL_UNCLE:
                    return getExtendedRelationShips(member, Male, Male, isBloodline(name));
                case DAUGHTER:
                    return getChildrenRelationShip(member, Female);
                case SON:
                    return getChildrenRelationShip(member, Male);
                case BROTHER_IN_LAW:
                    return getInLawRelationShips(member, Male, isBloodline(name));
                case SISTER_IN_LAW:
                    return getInLawRelationShips(member, Female, isBloodline(name));
            }
        }
        return PERSON_NOT_FOUND;
    }

    private boolean isBloodline(String name) {
        return lookup.entrySet().stream().anyMatch(memberEntry -> memberEntry.getKey().containsKey(name) && memberEntry.getValue().getName().equals(name));
    }

    private Optional<Map.Entry<Map<String, Gender>, Member>> getMembers(String name) {
        return lookup.entrySet().stream().filter(memberEntry -> memberEntry.getKey().containsKey(name))
                .findFirst();
    }

    private List<String> getInLawRelationShips(Member member, Gender gender, boolean isBloodMember) {
        List<String> inLawRelationsList = new ArrayList<>();
        List<String> siblings = getSiblingRelationShips(member, true);
        if (siblings.size() > 0)
            if (!isBloodMember) // spouse of blood member
                inLawRelationsList.addAll(siblings.stream().filter(sibling ->
                        lookup.keySet().stream().anyMatch(currentSibling -> currentSibling.containsKey(sibling) && currentSibling.containsValue(gender)))
                        .collect(Collectors.toList()));
            else
                siblings.forEach(sibling -> lookup.entrySet().stream().filter(value -> value.getKey().containsKey(sibling) && !value.getKey().containsValue(gender))
                        .map(currentSibling -> currentSibling.getValue().getSpouse())
                        .forEach(value -> value.ifPresent(spouse -> {
                            inLawRelationsList.addAll(new ArrayList<>(spouse.keySet()));
                        })));
        return inLawRelationsList;
    }

    private List<String> getSiblingRelationShips(Member member, boolean isBloodMember) {
        List<String> siblings = new ArrayList<>();
        if (!isBloodMember) return siblings;
        List<Member> siblingList = new ArrayList<>(member.getParent().getChildren());
        siblingList.remove(member);
        siblings = siblingList.stream().map(Member::getName).collect(toList());
        return siblings;
    }

    private List<String> getExtendedRelationShips(Member member, Gender parentGender, Gender relationGender, boolean isBloodMember) {
        List<String> extendedRelationsList = new ArrayList<>();
        if (!isBloodMember) return extendedRelationsList;
        if (!member.getParent().getGender().equals(parentGender)) return extendedRelationsList;

        List<String> extendedRelations = getSiblingRelationShips(member.getParent(), true);
       extendedRelationsList.addAll(extendedRelations.stream().filter(relative ->
                lookup.keySet().stream().anyMatch(currentRelative ->
                        currentRelative.containsKey(relative) && currentRelative.containsValue(relationGender))).collect(toList()));
       return extendedRelationsList;
    }

    private List<String> getChildrenRelationShip(Member member, Gender gender) {
        List<String> childList = new ArrayList<>();
        member.getChildren().forEach(child -> {
            if (child.getGender().equals(gender)) childList.add(child.getName());
        });
        return childList;
    }
}

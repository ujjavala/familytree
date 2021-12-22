package com.family.tree.service.impl;

import com.family.tree.enums.Gender;
import com.family.tree.enums.Message;
import com.family.tree.model.Member;
import com.family.tree.service.ExtendedRelationService;
import com.family.tree.service.InLawService;
import com.family.tree.service.MemberService;

import java.util.*;
import static com.family.tree.enums.Gender.FEMALE;
import static com.family.tree.enums.Gender.MALE;
import static com.family.tree.enums.Message.*;
import static com.family.tree.enums.Relationship.*;
import static com.family.tree.util.MemberUtil.*;

public class MemberServiceImpl implements MemberService {
    private final LinkedHashMap<Map<String, Gender>, Member> lookup;
    private final InLawService inLawService;
    private final ExtendedRelationService extendedRelationService;

    public MemberServiceImpl(LinkedHashMap<Map<String, Gender>, Member> lookup, InLawService inLawService, ExtendedRelationService extendedRelationService) {
        this.lookup = lookup;
        this.inLawService = inLawService;
        this.extendedRelationService = extendedRelationService;
    }

    @Override
    public Message addChild(String parentName, String name, String gender) {
        if (isParentNotPresent(parentName, lookup)) {
            return PERSON_NOT_FOUND;
        } else return addIfValidParent(parentName, name, gender);
    }

    private Message addIfValidParent(String parentName, String name, String gender) {
        if (isParentNotFemale(parentName, lookup)) {
            return CHILD_ADDITION_FAILED;
        }
        return addChildToValidParent(parentName, name, gender);
    }

    @Override
    public Object getRelationship(String name, String relationship) {
        if (isInvalidRelationship(relationship))
            return NONE;
        if (getMembers(name, lookup).isPresent()) {
            return getRelationShipForValidMember(name, relationship);
        }
        return PERSON_NOT_FOUND;
    }

    private Message addChildToValidParent(String parentName, String name, String gender) {
        Member parent = lookup.get(Map.of(parentName, FEMALE));
        Member child = new Member(name, Gender.valueOf(gender.toUpperCase()), Optional.empty());
        child.setGeneration(parent.getGeneration()+1);
        parent.addChild(child);
        return CHILD_ADDED;
    }


    private List<String> getRelationShipForValidMember(String name, String relationship) {
        List<String> foundMembers;
        Member member = getMembers(name, lookup).get().getValue();
        if (relationship.contains("IN_LAW")) {
            foundMembers = checkForInLawRelationship(name, relationship, member);
        } else if (relationship.contains("AUNT") || relationship.contains("UNCLE")) {
            foundMembers = checkForExtendedRelationship(name, relationship, member);
        } else {
            foundMembers = getDirectRelationships(name, relationship, member);
        }
        return foundMembers;
    }

    private List<String> getDirectRelationships(String name, String relationship, Member member) {
        List<String> foundMembers;
        if (relationship.equals(SIBLINGS.relationValue)) {
            foundMembers = getSiblingRelationShips(member, isBloodline(name, lookup));
        } else foundMembers = checkForChildrenRelationship(relationship, member);
        return foundMembers;
    }

    private List<String> checkForChildrenRelationship(String relationship, Member member) {
        if (relationship.equals(DAUGHTER.relationValue)) return getChildrenRelationShip(member, FEMALE);
        return getChildrenRelationShip(member, MALE);
    }

    private List<String> checkForInLawRelationship(String name, String relationship, Member member) {
        if (BROTHER_IN_LAW.relationValue.equals(relationship)) {
            return inLawService.getInLawRelationShips(member, MALE, isBloodline(name, lookup), lookup);
        }
        return inLawService.getInLawRelationShips(member, FEMALE, isBloodline(name, lookup), lookup);

    }

    private List<String> checkForExtendedRelationship(String name, String relationship, Member member) {
        if (relationship.contains("MATERNAL"))
            return extendedRelationService.getMaternalExtendedRelationship(name, relationship, member, lookup);
        else return extendedRelationService.getPaternalExtendedRelationship(name, relationship, member, lookup);
    }

    private List<String> getChildrenRelationShip(Member member, Gender gender) {
        List<String> childList = new ArrayList<>();
        member.getChildren().forEach(child -> {
            if (child.getGender().equals(gender)) childList.add(child.getName());
        });
        return childList;
    }

    @Override
    public Message deleteChild(String parentName, String name) {
        return null; //need to implement
    }

    @Override
    public Integer getGeneration(String name) {
        return null; //need to implement
    }
}

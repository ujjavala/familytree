package com.family.tree.service.impl;

import com.family.tree.enums.Gender;
import com.family.tree.enums.Relationship;
import com.family.tree.model.Member;
import com.family.tree.service.ExtendedRelationService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.family.tree.enums.Gender.FEMALE;
import static com.family.tree.enums.Gender.MALE;
import static com.family.tree.enums.Relationship.*;
import static com.family.tree.util.MemberUtil.*;
import static java.util.stream.Collectors.toList;

public class ExtendedRelationServiceImpl implements ExtendedRelationService {

    @Override
    public List<String> getExtendedRelationShips(Member member, Gender parentGender, Gender relationGender, boolean isBloodMember, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        List<String> extendedRelationsList = new ArrayList<>();
        if (isBloodLineOrInvalidParent(member, parentGender, isBloodMember)) return extendedRelationsList;
        getExtendedRelatives(member, relationGender, lookup, extendedRelationsList);
        return extendedRelationsList;
    }

    private void getExtendedRelatives(Member member, Gender relationGender, LinkedHashMap<Map<String, Gender>, Member> lookup, List<String> extendedRelationsList) {
        List<String> extendedRelations = getSiblingRelationShips(member.getParent(), true);
        extendedRelationsList.addAll(extendedRelations.stream().filter(getRelativeMatch(relationGender, lookup)).collect(toList()));
    }

    private boolean isBloodLineOrInvalidParent(Member member, Gender parentGender, boolean isBloodMember) {
        return !isBloodMember || !isGenderMatchWithParent(member, parentGender);
    }

    @Override
    public List<String> getPaternalExtendedRelationship(String name, String relationship, Member member, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return getRelatives(name, relationship, member, lookup, PATERNAL_AUNT, MALE, PATERNAL_UNCLE);
    }

    @Override
    public List<String> getMaternalExtendedRelationship(String name, String relationship, Member member, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return getRelatives(name, relationship, member, lookup, MATERNAL_AUNT, FEMALE, MATERNAL_UNCLE);
    }

    private List<String> getRelatives(String name, String relationship, Member member, LinkedHashMap<Map<String, Gender>, Member> lookup, Relationship aunt, Gender female, Relationship uncle) {
        if (aunt.relationValue.equals(relationship)) {
            return getExtendedRelationShips(member, female, FEMALE, isBloodline(name, lookup), lookup);
        }
        return getExtendedRelationShips(member, female, MALE, isBloodline(name, lookup), lookup);
    }
}

package com.family.tree.service;

import com.family.tree.enums.Gender;
import com.family.tree.model.Member;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ExtendedRelationService {
    List<String> getExtendedRelationShips(Member member, Gender parentGender, Gender relationGender, boolean isBloodMember, LinkedHashMap<Map<String, Gender>, Member> lookup);

    List<String> getPaternalExtendedRelationship(String name, String relationship, Member member, LinkedHashMap<Map<String, Gender>, Member> lookup);

    List<String> getMaternalExtendedRelationship(String name, String relationship, Member member, LinkedHashMap<Map<String, Gender>, Member> lookup);
}

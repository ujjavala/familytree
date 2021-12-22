package com.family.tree.service;

import com.family.tree.enums.Gender;
import com.family.tree.model.Member;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface InLawService {
    List<String> getInLawRelationShips(Member member, Gender gender, boolean isBloodMember, LinkedHashMap<Map<String, Gender>, Member> lookup);
}

package com.family.tree.service.impl;

import com.family.tree.enums.Gender;
import com.family.tree.model.Member;
import com.family.tree.service.FamilyLookupService;

import java.util.LinkedHashMap;
import java.util.Map;


public class FamilyLookupServiceImpl implements FamilyLookupService {

    private final LinkedHashMap<Map<String, Gender>, Member> lookup = new LinkedHashMap<>();


    public FamilyLookupServiceImpl(Member root_KingArthur) {
        putValuesToLookup(root_KingArthur);
    }

    @Override
    public LinkedHashMap<Map<String, Gender>, Member> getLookup() {
        return lookup;
    }

    private void putValuesToLookup(Member member) {
        addMember(member);
        if (member.getChildren().iterator().hasNext())
            member.getChildren().forEach(this::putValuesToLookup);
    }

    private void addMember(Member member) {
        lookup.put(Map.of(member.getName(), member.getGender()), member);
        if (member.getSpouse().isPresent()) {
            Map<String, Gender> spouse = member.getSpouse().get();
            lookup.put(Map.of(getNonBloodlineMemberName(spouse),
                    getNonBloodLineMemberGender(spouse)), member);
        }
    }

    private Gender getNonBloodLineMemberGender(Map<String, Gender> spouse) {
        return spouse.values().stream().findFirst().orElseThrow();
    }

    private String getNonBloodlineMemberName(Map<String, Gender> spouse) {
        return spouse.keySet().stream().findFirst().orElseThrow();
    }
}

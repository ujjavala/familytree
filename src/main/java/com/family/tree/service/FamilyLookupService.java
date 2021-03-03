package com.family.tree.service;

import com.family.tree.enums.Gender;
import com.family.tree.model.Member;

import java.util.LinkedHashMap;
import java.util.Map;


public class FamilyLookupService {

    private final LinkedHashMap<Map<String,Gender>, Member> lookup = new LinkedHashMap<>();
    public FamilyLookupService(Member root_KingArthur) {
        putValuesToLookup(root_KingArthur);
    }
    public LinkedHashMap<Map<String,Gender>, Member> getLookup() {
        return lookup;
    }

    private void putValuesToLookup(Member member) {
        addMember(member);
        if (member.getChildren().iterator().hasNext())
            member.getChildren().forEach(this::putValuesToLookup);
    }

    private void addMember(Member member) {
            lookup.put(Map.of(member.getName(),member.getGender()),member);
         if(member.getSpouse().isPresent())
            lookup.put(Map.of(member.getSpouse().get().keySet().stream().findFirst().orElseThrow(),
                    member.getSpouse().get().values().stream().findFirst().orElseThrow()), member);
    }
}

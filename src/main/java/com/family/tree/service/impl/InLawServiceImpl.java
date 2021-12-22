package com.family.tree.service.impl;

import com.family.tree.enums.Gender;
import com.family.tree.model.Member;
import com.family.tree.service.InLawService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.family.tree.util.MemberUtil.getRelativeMatch;
import static com.family.tree.util.MemberUtil.getSiblingRelationShips;
import static com.family.tree.util.StreamUtil.getEntryStream;

public class InLawServiceImpl implements InLawService {

    private void getInLawRelationshipForSpouse(Gender gender, List<String> inLawRelationsList, List<String> siblings, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        inLawRelationsList.addAll(siblings.stream().filter(getRelativeMatch(gender, lookup))
                .collect(Collectors.toList()));
    }

    private Stream<Map.Entry<Map<String, Gender>, Member>> getSiblingStream(Gender gender, String sibling, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return getEntryStream(lookup).filter(value -> value.getKey().containsKey(sibling) && !value.getKey().containsValue(gender));
    }

    @Override
    public List<String> getInLawRelationShips(Member member, Gender gender, boolean isBloodMember, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        List<String> inLawRelationsList = new ArrayList<>();
        List<String> siblings = getSiblingRelationShips(member, true);
        if (siblings.size() > 0)
            getSiblingInLaws(gender, isBloodMember, lookup, inLawRelationsList, siblings);
        return inLawRelationsList;
    }

    private void getSiblingInLaws(Gender gender, boolean isBloodMember, LinkedHashMap<Map<String, Gender>, Member> lookup, List<String> inLawRelationsList, List<String> siblings) {
        if (!isBloodMember) // spouse of blood member
            getInLawRelationshipForSpouse(gender, inLawRelationsList, siblings, lookup);
        else
            getInLawRelationshipForBloodLineMember(gender, inLawRelationsList, siblings, lookup);
    }

    private void getInLawRelationshipForBloodLineMember(Gender gender, List<String> inLawRelationsList, List<String> siblings, LinkedHashMap<Map<String, Gender>, Member> lookup) {
        siblings.forEach(sibling -> getSpouseForSiblingStream(gender, lookup, sibling)
                .forEach(value -> value.ifPresent(spouse -> {
                    inLawRelationsList.addAll(new ArrayList<>(spouse.keySet()));
                })));
    }

    private Stream<Optional<Map<String, Gender>>> getSpouseForSiblingStream(Gender gender, LinkedHashMap<Map<String, Gender>, Member> lookup, String sibling) {
        return getSiblingStream(gender, sibling, lookup)
                .map(currentSibling -> currentSibling.getValue().getSpouse());
    }

}

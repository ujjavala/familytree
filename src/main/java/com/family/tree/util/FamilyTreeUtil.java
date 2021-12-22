package com.family.tree.util;

import com.family.tree.model.Member;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.family.tree.enums.Gender.FEMALE;
import static com.family.tree.enums.Gender.MALE;
import static com.family.tree.util.Constants.*;
import static java.util.Arrays.asList;
import static java.util.stream.Stream.of;

public class FamilyTreeUtil {

    public static Member getRoot() {
        Member root_KingArthur = new Member("King Arthur", MALE, Optional.of(Map.of("Queen Margret", FEMALE)));
        root_KingArthur.setGeneration(ROOT_GEN);
        generateTree(root_KingArthur);
        return root_KingArthur;
    }

    private static void generateTree(Member root_KingArthur) {
        addFirstGeneration(root_KingArthur);

        Member firstGenerationChild_Bill = getChild(root_KingArthur, "Bill");
        Member firstGenerationChild_Percy = getChild(root_KingArthur, "Percy");
        Member firstGenerationChild_Ronald = getChild(root_KingArthur, "Ronald");
        Member firstGenerationChild_Minerva = getChild(root_KingArthur, "Minerva");

        addSecondGeneration(firstGenerationChild_Bill, firstGenerationChild_Percy, firstGenerationChild_Ronald, firstGenerationChild_Minerva);

        Member secondGenerationChild_Victoire = getChild(firstGenerationChild_Bill, "Victoire");
        Member secondGenerationChild_Rose = getChild(firstGenerationChild_Ronald, "Rose");
        Member secondGenerationChild_James = getChild(firstGenerationChild_Minerva, "James");
        Member secondGenerationChild_Albus = getChild(firstGenerationChild_Minerva, "Albus");

        addThirdGeneration(secondGenerationChild_Victoire, secondGenerationChild_Rose, secondGenerationChild_James, secondGenerationChild_Albus);
    }

    private static void addThirdGeneration(Member child_Victoire, Member child_Rose, Member child_James, Member child_Albus) {

        List.of(new Member("Remus", MALE, Optional.empty())).forEach(child -> {
            child.setGeneration(THIRD_GEN);
            child_Victoire.addChild(child);
        });


        asList(new Member("Draco", MALE, Optional.empty()),
                new Member("Aster", FEMALE, Optional.empty())).forEach(child1 -> {
            child1.setGeneration(THIRD_GEN);
            child_Rose.addChild(child1);
        });

        List.of(new Member("William", MALE, Optional.empty())).forEach(child -> {
            child.setGeneration(THIRD_GEN);
            child_James.addChild(child);
        });

        asList(new Member("Ron", MALE, Optional.empty()),
                new Member("Ginny", FEMALE, Optional.empty())).forEach(child -> {
            child.setGeneration(THIRD_GEN);
            child_Albus.addChild(child);
        });
    }

    private static void addSecondGeneration(Member child_Bill, Member child_Percy, Member child_Ronald, Member child_Minerva) {
        asList(new Member("Victoire", FEMALE, Optional.of(Map.of("Ted", MALE))),
                new Member("Dominique", FEMALE, Optional.empty()),
                new Member("Louis", MALE, Optional.empty())).forEach(child -> {
            child.setGeneration(SECOND_GEN);
            child_Bill.addChild(child);
        });

        of("Molly", "Lucy").map(childName -> new Member(childName, FEMALE, Optional.empty())).forEach(child -> {
            child.setGeneration(SECOND_GEN);
            child_Percy.addChild(child);
        });

        asList(new Member("Hugo", MALE, Optional.empty()),
                new Member("Rose", FEMALE, Optional.of(Map.of("Malfoy", MALE)))).forEach(child -> {
            child.setGeneration(SECOND_GEN);
            child_Ronald.addChild(child);
        });

        asList(new Member("James", MALE, Optional.of(Map.of("Darcy", FEMALE))),
                new Member("Albus", MALE, Optional.of(Map.of("Alice", FEMALE))),
                new Member("Lily", FEMALE, Optional.empty())).forEach(child -> {
            child.setGeneration(SECOND_GEN);
            child_Minerva.addChild(child);
        });
    }

    private static void addFirstGeneration(Member root_KingArthur) {
        asList(new Member("Bill", MALE, Optional.of(Map.of("Flora", FEMALE))),
                new Member("Charlie", MALE, Optional.empty()),
                new Member("Percy", MALE, Optional.of(Map.of("Audrey", FEMALE))),
                new Member("Ronald", MALE, Optional.of(Map.of("Helen", FEMALE))),
                new Member("Minerva", FEMALE, Optional.of(Map.of("Harry", MALE)))).forEach(child -> {
            child.setGeneration(FIRST_GEN);
            root_KingArthur.addChild(child);
        });
    }

    private static Member getChild(Member member, String childName) {
        return member.getChildren().stream().filter(child -> child.getName().equals(childName)).findFirst().orElseThrow();
    }

}

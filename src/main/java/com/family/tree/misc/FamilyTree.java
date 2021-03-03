package com.family.tree.misc;

import com.family.tree.model.Member;

import java.util.Map;
import java.util.Optional;

import static com.family.tree.enums.Gender.Female;
import static com.family.tree.enums.Gender.Male;

public class FamilyTree {
    private final Member root_KingArthur = new Member("King Arthur", Male, Optional.of(Map.of("Queen Margret", Female)));

    public FamilyTree()
    {
        generateTree();
    }

    public Member getRoot() {
        return root_KingArthur;
    }

    private  void generateTree() {
        root_KingArthur.addChild(new Member("Bill", Male, Optional.of(Map.of("Flora", Female))));
        root_KingArthur.addChild(new Member("Charlie", Male, Optional.empty()));
        root_KingArthur.addChild(new Member("Percy", Male, Optional.of(Map.of("Audrey", Female))));
        root_KingArthur.addChild(new Member("Ronald", Male, Optional.of(Map.of("Helen", Female))));
        root_KingArthur.addChild(new Member("Minerva", Female, Optional.of(Map.of("Harry", Male))));

        Member child_Bill = root_KingArthur.getChildren().stream().filter(child -> child.getName().equals("Bill")).findFirst().orElseThrow();
        child_Bill.addChild(new Member("Victoire", Female, Optional.of(Map.of("Ted", Male))));
        child_Bill.addChild(new Member("Dominique", Female, Optional.empty()));
        child_Bill.addChild(new Member("Louis", Male, Optional.empty()));

        Member child_Percy = root_KingArthur.getChildren().stream().filter(child -> child.getName().equals("Percy")).findFirst().orElseThrow();
        child_Percy.addChild(new Member("Molly", Female, Optional.empty()));
        child_Percy.addChild(new Member("Lucy", Female, Optional.empty()));

        Member child_Ronald = root_KingArthur.getChildren().stream().filter(child -> child.getName().equals("Ronald")).findFirst().orElseThrow();
        child_Ronald.addChild(new Member("Hugo", Male, Optional.empty()));
        child_Ronald.addChild(new Member("Rose", Female, Optional.of(Map.of("Malfoy", Male))));

        Member child_Minerva = root_KingArthur.getChildren().stream().filter(child -> child.getName().equals("Minerva")).findFirst().orElseThrow();
        child_Minerva.addChild(new Member("James", Male, Optional.of(Map.of("Darcy", Female))));
        child_Minerva.addChild(new Member("Albus", Male, Optional.of(Map.of("Alice", Female))));
        child_Minerva.addChild(new Member("Lily", Female, Optional.empty()));

        Member child_Victoire = child_Bill.getChildren().stream().filter(child -> child.getName().equals("Victoire")).findFirst().orElseThrow();
        child_Victoire.addChild(new Member("Remus", Male, Optional.empty()));

        Member child_Rose = child_Ronald.getChildren().stream().filter(child -> child.getName().equals("Rose")).findFirst().orElseThrow();
        child_Rose.addChild(new Member("Draco", Male, Optional.empty()));
        child_Rose.addChild(new Member("Aster", Female, Optional.empty()));

        Member child_James = child_Minerva.getChildren().stream().filter(child -> child.getName().equals("James")).findFirst().orElseThrow();
        child_James.addChild(new Member("William", Male, Optional.empty()));

        Member child_Albus = child_Minerva.getChildren().stream().filter(child -> child.getName().equals("Albus")).findFirst().orElseThrow();
        child_Albus.addChild(new Member("Ron", Male, Optional.empty()));
        child_Albus.addChild(new Member("Ginny", Female, Optional.empty()));
    }

}

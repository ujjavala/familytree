package com.family.tree.model;

import com.family.tree.enums.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Member {
    private final String name;
    private final Optional<Map<String, Gender>> spouse;
    private final List<Member> children = new ArrayList<>();
    private Gender gender;
    private Member parent;
    private int generation;

    public Member(String name, Gender gender, Optional<Map<String, Gender>> spouse) {
        this.name = name;
        this.gender = gender;
        this.spouse = spouse;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Member getParent() {
        return parent;
    }

    public void setParent(Member parent) {
        this.parent = parent;
    }

    public List<Member> getChildren() {
        return children;
    }

    public void addChild(Member child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void deleteChild(Member child) {
        child.setParent(null);
        this.children.remove(child);
    }

    public Optional<Map<String, Gender>> getSpouse() {
        return spouse;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}

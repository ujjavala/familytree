package com.family.tree.enums;

public enum Relationship {

    PATERNAL_UNCLE("Paternal-Uncle"),
    PATERNAL_AUNT("Paternal-Aunt"),
    MATERNAL_UNCLE("Maternal-Uncle"),
    MATERNAL_AUNT("Maternal-Aunt"),
    SISTER_IN_LAW("Sister-In-Law"),
    BROTHER_IN_LAW("Brother-In-Law"),
    SON("Son"),
    DAUGHTER("Daughter"),
    SIBLINGS("Siblings");

    public final String relationValue;

    Relationship(String relationValue) {
        this.relationValue = relationValue;
    }
}

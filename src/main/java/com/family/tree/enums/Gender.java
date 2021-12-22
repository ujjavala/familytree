package com.family.tree.enums;

public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    INTERSEX("Intersex"),
    TRANSGENDER("Transgender"),
    GENDER_NEUTRAL("GenderNeutral"),
    NON_BINARY("NonBinary"),
    A_GENDER("AGender"),
    PAN_GENDER("PanGender"),
    GENDER_QUEER("GenderQueer"),
    TWO_SPIRIT("TwoSpirit"),
    THIRD_GENDER("ThirdGender");

    public final String genderValue;

    Gender(String genderValue) {
        this.genderValue = genderValue;
    }
}

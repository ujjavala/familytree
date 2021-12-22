package com.family.tree.service;

import com.family.tree.enums.Gender;
import com.family.tree.model.Member;

import java.util.LinkedHashMap;
import java.util.Map;

public interface FamilyLookupService {
    LinkedHashMap<Map<String, Gender>, Member> getLookup();
}

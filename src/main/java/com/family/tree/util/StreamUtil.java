package com.family.tree.util;

import com.family.tree.enums.Gender;
import com.family.tree.model.Member;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public  class StreamUtil {
    public static Stream<Map.Entry<Map<String, Gender>, Member>> getEntryStream(LinkedHashMap<Map<String, Gender>, Member> lookup) {
        return lookup.entrySet().stream();
    }
}

package com.family.tree.service;

import com.family.tree.enums.Message;

public interface MemberService {
    Message addChild(String parentName, String name, String gender);
    Object getRelationship(String name, String relationship);
    Message deleteChild(String parentName, String name);
    Integer getGeneration(String name);
}

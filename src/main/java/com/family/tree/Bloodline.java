package com.family.tree;

import com.family.tree.enums.Gender;
import com.family.tree.enums.Message;
import com.family.tree.io.MemberInputParser;
import com.family.tree.io.MemberOutputParser;
import com.family.tree.model.Member;
import com.family.tree.service.ExtendedRelationService;
import com.family.tree.service.InLawService;
import com.family.tree.service.MemberService;
import com.family.tree.service.impl.*;
import com.family.tree.util.FamilyTreeUtil;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bloodline {
    public static void main(String[] args) {
        try {
            Member familyTreeRoot = FamilyTreeUtil.getRoot();
            LinkedHashMap<Map<String, Gender>, Member> lookup = new FamilyLookupServiceImpl(familyTreeRoot).getLookup();
            InLawService inLawService = new InLawServiceImpl();
            ExtendedRelationService extendedRelationService = new ExtendedRelationServiceImpl();
            MemberService memberService = new MemberServiceImpl(lookup,inLawService, extendedRelationService);
            List<String[]> parser = new MemberInputParser().parse(args[0]);
            List<Object> output = new FamilyTreeServiceImpl(parser, memberService).executeAction();
            new MemberOutputParser().parse(output);
        } catch (IOException e) {
            System.out.println(Message.FILE_NOT_FOUND);
        }
    }
}
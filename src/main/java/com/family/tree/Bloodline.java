package com.family.tree;

import com.family.tree.enums.Message;
import com.family.tree.service.FamilyLookupService;
import com.family.tree.misc.FamilyTree;
import com.family.tree.service.FamilyTreeService;
import com.family.tree.service.MemberService;
import com.family.tree.io.MemberInputParser;
import com.family.tree.io.MemberOutputParser;

import java.io.IOException;
import java.util.List;

public  class Bloodline {
    public static void main(String[] args)   {
        try {
            List<Object> output = new FamilyTreeService(new MemberInputParser().parse(),
                      new MemberService(new FamilyLookupService(new FamilyTree().getRoot()).getLookup()))
                      .executeAction();
            new MemberOutputParser().parse(output);
        } catch (IOException e) {
            System.out.println(Message.FILE_NOT_FOUND);
        }
    }
}
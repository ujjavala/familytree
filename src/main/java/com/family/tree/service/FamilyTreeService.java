package com.family.tree.service;

import com.family.tree.enums.Action;
import com.family.tree.enums.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.family.tree.misc.Constants.ADD_CHILD_LENGTH;
import static com.family.tree.misc.Constants.GET_RELATIONSHIP_LENGTH;

public class FamilyTreeService {
    private final List<String[]> actionList;
    private final List<Object> output = new ArrayList<>();

    private final MemberService memberService;

    public FamilyTreeService(List<String[]> actionList, MemberService memberService) {
        this.actionList = actionList;
        this.memberService = memberService;
    }

    public List<Object> executeAction() {
        actionList.forEach(action -> {
            if (Objects.equals(action[0], Action.ADD_CHILD.toString()) && action.length == ADD_CHILD_LENGTH) {
                output.add(memberService.addChild(action[1], action[2], action[3]));
            }
            else if (Objects.equals(action[0], Action.GET_RELATIONSHIP.toString()) && action.length == GET_RELATIONSHIP_LENGTH)
                output.add(memberService.getRelationship(action[1], action[2]));
            else output.add(Message.INCORRECT_INPUT);
        });
        return output;
    }
}

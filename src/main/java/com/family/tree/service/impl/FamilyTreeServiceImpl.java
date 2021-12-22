package com.family.tree.service.impl;

import com.family.tree.enums.Action;
import com.family.tree.enums.Message;
import com.family.tree.service.FamilyTreeService;
import com.family.tree.service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.family.tree.util.Constants.ADD_CHILD_LENGTH;
import static com.family.tree.util.Constants.GET_RELATIONSHIP_LENGTH;

public class FamilyTreeServiceImpl implements FamilyTreeService {
    private final List<String[]> actionList;
    private final List<Object> output = new ArrayList<>();
    private final MemberService memberService;

    public FamilyTreeServiceImpl(List<String[]> actionList, MemberService memberService) {
        this.actionList = actionList;
        this.memberService = memberService;
    }

    @Override
    public List<Object> executeAction() {
        actionList.forEach(this::getActionAndExecute);
        return output;
    }

    private void getActionAndExecute(String[] action) {
        if (shouldAddChild(action)) {
            output.add(memberService.addChild(action[1], action[2], action[3]));
        } else if (shouldFetchRelationship(action)) {
            output.add(memberService.getRelationship(action[1], action[2]));
        } else {
            output.add(Message.INCORRECT_INPUT);
        }
    }

    private boolean shouldAddChild(String[] action) {
        return Objects.equals(action[0], Action.ADD_CHILD.toString()) && action.length == ADD_CHILD_LENGTH;
    }

    private boolean shouldFetchRelationship(String[] action) {
        return Objects.equals(action[0], Action.GET_RELATIONSHIP.toString()) && action.length == GET_RELATIONSHIP_LENGTH;
    }
}

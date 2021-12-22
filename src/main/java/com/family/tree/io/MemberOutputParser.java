package com.family.tree.io;

import com.family.tree.enums.Message;

import java.util.ArrayList;
import java.util.List;

public class MemberOutputParser {

    public void parse(List<Object> outputList) {
        outputList.forEach(output -> {
            ifOutputIsMessage(output);
            ifOutputIsAnArrayAndIsNonEmpty(output);
            ifOutputArrayIsEmpty(output);
        });
    }

    private void ifOutputArrayIsEmpty(Object output) {
        if (output.getClass().equals(ArrayList.class) && ((ArrayList) output).size() == 0)
            System.out.println(Message.NONE);
    }

    private void ifOutputIsAnArrayAndIsNonEmpty(Object output) {
        if (ifArraySizeIsNotZero(output)) {
            ((ArrayList) output).forEach(value -> System.out.print(value + " "));
            System.out.println();
        }
    }

    private boolean ifArraySizeIsNotZero(Object output) {
        return output.getClass().equals(ArrayList.class) && ((ArrayList) output).size() > 0;
    }

    private void ifOutputIsMessage(Object output) {
        if (output.getClass().equals(Message.class)) System.out.println(output);
    }
}

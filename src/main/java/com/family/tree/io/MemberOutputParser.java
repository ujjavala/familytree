package com.family.tree.io;

import com.family.tree.enums.Message;

import java.util.ArrayList;
import java.util.List;

public class MemberOutputParser {

    public void parse(List<Object> outputList) {
        outputList.forEach(output->{
            if(output.getClass().equals(Message.class)) System.out.println(output);
            if(output.getClass().equals(ArrayList.class) && ((ArrayList) output).size()>0)
            {
                ((ArrayList) output).forEach(value->System.out.print(value+" "));
                System.out.println();
            }
            if(output.getClass().equals(ArrayList.class) && ((ArrayList) output).size()==0)
                System.out.println(Message.NONE);
        });
    }
}

package com.project.course.project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabhjyotsingh on 19/05/17.
 */

public class Options {

    List<String> options;
    int answer;

    Options(){
        options = new ArrayList<String>();
        answer = -1;
    }

    void setOptions(String[] ops){
        for(int i=0; i < ops.length; i++)
            options.add(ops[i]);
    }

    void setAnswer(int ans){
        answer = ans;
    }

    @Override
    public String toString() {
        return options.toString();
    }
}

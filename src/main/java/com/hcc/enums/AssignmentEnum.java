package com.hcc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AssignmentEnum {

    ASSIGNMENT_1(1, "Assignment 1"),

    ASSIGNMENT_2(2, "Assignment 2"),

    ASSIGNMENT_3(3, "Assignment 3"),

    ASSIGNMENT_4(4, "Assignment 4"),

    ASSIGNMENT_5(5, "Assignment 5");

    private final int assignmentNumber;

    private final String assignmentDescription;

    AssignmentEnum(int assignmentNumber, String assignmentDescription) {
        this.assignmentNumber = assignmentNumber;
        this.assignmentDescription = assignmentDescription;
    }

    public int getAssignmentNumber() {
        return assignmentNumber;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

}

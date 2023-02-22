package com.hcc.dtos;

import com.hcc.entities.Assignment;
import com.hcc.enums.AssignmentEnum;
import com.hcc.enums.AssignmentStatusEnum;

public class AssignmentResponceDto {

    private final Assignment assignment;

    private final AssignmentStatusEnum[] statusEnums = AssignmentStatusEnum.values();

    private AssignmentEnum[] assignmentEnums = AssignmentEnum.values();

    public AssignmentResponceDto(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public AssignmentStatusEnum[] getStatusEnums() {
        return statusEnums;
    }

    public AssignmentEnum[] getAssignmentEnums() {
        return assignmentEnums;
    }
}

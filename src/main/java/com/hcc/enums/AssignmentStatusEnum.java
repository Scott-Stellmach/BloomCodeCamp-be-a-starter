package com.hcc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AssignmentStatusEnum {
    IN_PROGRESS(1, "In Progress"),
    SUBMITTED(2, "Submitted"),
    REJECTED(3, "Rejected"),
    COMPLETED(4, "Completed");

    private final int assignmentStatusNumber;

    private final String assignmentStatusMessage;

    AssignmentStatusEnum(int assignmentStatusNumber, String assignmentStatusMessage) {
        this.assignmentStatusNumber = assignmentStatusNumber;
        this.assignmentStatusMessage = assignmentStatusMessage;
    }

    public int getAssignmentStatusNumber() {
        return assignmentStatusNumber;
    }

    public String getAssignmentStatusMessage() {
        return assignmentStatusMessage;
    }

}

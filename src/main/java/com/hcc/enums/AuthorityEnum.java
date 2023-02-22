package com.hcc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AuthorityEnum {
    LEARNER_ROLE,
    CODE_REVIEWER_ROLE,
    ADMIN_ROLE
}

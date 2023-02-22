package com.hcc.services;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AuthorityEnum;
import com.hcc.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Set<Assignment> findAssignmentsByUser(User user) {

        boolean hasCodeReviewerRole = user.getAuthorities().stream()
                .filter(auth -> AuthorityEnum.CODE_REVIEWER_ROLE.name().equals(auth.getAuthority()))
                .count() > 0;

        if (hasCodeReviewerRole) {
            return assignmentRepository.findAssignmentByCodeReviewer(user);
        } else {
            return assignmentRepository.findAssignmentByUser(user);
        }
    }

    public Assignment findAssignmentsById(Long id) {

        return assignmentRepository.findById(id)
                .orElse(new Assignment());
    }

    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignmentById(Long id) {
        assignmentRepository.deleteById(id);
    }
}

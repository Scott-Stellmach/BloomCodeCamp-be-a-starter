package com.hcc.services;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.enums.AuthorityEnum;
import com.hcc.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

    public Assignment saveAssignment(User user) {

        Assignment assignment = new Assignment();
        assignment.setUser(user);
        assignment.setStatus(AssignmentStatusEnum.IN_PROGRESS.getAssignmentStatusMessage());
        assignment.setNumber(nextAssignmentToSubmit(user));

        return assignmentRepository.save(assignment);
    }

    public Assignment saveUpdatedAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignmentById(Long id) {
        assignmentRepository.deleteById(id);
    }

    private Integer nextAssignmentToSubmit(User user) {

        Set<Assignment> assignmentSet = assignmentRepository.findAssignmentByUser(user);

        if (assignmentSet == null) {
            return 1;
        }

        Optional<Integer> sortedAssignment = assignmentSet.stream()
                .sorted((a1, a2) -> {
                    if (a1.getNumber() == null) {
                        return 1;
                    }
                    if (a2.getNumber() == null) {
                        return 1;
                    }
                    return a2.getNumber().compareTo(a1.getNumber());
                }).map(assignment -> {
                    if(assignment.getNumber() == null){
                        return 1;
                    }
                    return assignment.getNumber() + 1;
                }).findFirst();
        return sortedAssignment.orElse(1);

    }
}

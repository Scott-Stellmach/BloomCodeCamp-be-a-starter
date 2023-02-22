package com.hcc.controllers;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    // TODO: Verify Authorization to get
    // TODO: Utilize the AssignmentResponseDto

    // TODO: Route all actions through the AssignmentService class, to the repo

    @Autowired
    private AssignmentService assignmentService;

    // Get all Assignments by User /api/assignments
    @GetMapping
    ResponseEntity<?> getAllAssignmentsByUser(@AuthenticationPrincipal User user) {

        Set<Assignment> assignments = assignmentService.findAssignmentsByUser(user);

        return ResponseEntity.ok(assignments);
    }


    // Get the Assignment by Id  /api/assignments/{id}
    @GetMapping("/{id}")
    ResponseEntity<?> getAssignmentById(@PathVariable Long id, @AuthenticationPrincipal User user) {

       Assignment assignment = assignmentService.findAssignmentsById(id);

       return ResponseEntity.ok(assignment);
    }

    // (UPDATE) Put Assignment by Id  /api/assignments/{id}
    @PutMapping("/{id}")
    ResponseEntity<Object> updateAssignment
        (@RequestBody Assignment assignment, @PathVariable Long id, @AuthenticationPrincipal User user) {

        Assignment assignments= assignmentService.findAssignmentsById(id);
        if (assignments == null) {
            return ResponseEntity.notFound().build();
        }

        assignment.setId(id);
        assignmentService.saveAssignment(assignment);
        return ResponseEntity.noContent().build();
    }

    // Post (NEW) Assignment /api/assignments
    @PostMapping
    ResponseEntity<Object> createAssignment(@RequestBody Assignment assignment, @AuthenticationPrincipal User user) {

        Assignment savedAssignment  = assignmentService.saveAssignment(assignment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAssignment.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    // DELETE an Assignment by Id  /api/assignments/{id}
    @DeleteMapping("/{id}")
    public void deleteAssignmentById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        assignmentService.deleteAssignmentById(id);
    }
}

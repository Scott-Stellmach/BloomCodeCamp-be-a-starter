package com.hcc.controllers;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.exceptions.AssignmentNotFoundException;
import com.hcc.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    // Get all Assignments by User /api/assignments
    @GetMapping("/api/assignments")
    public List<Assignment> getAllAssignmentsByUser(User user) {
        return assignmentRepository.findByUser(user);
    }

    // Get the Assignment by Id  /api/assignments/{id}
    @GetMapping("/api/assignments/{id}")
    public Assignment getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> optional = assignmentRepository.findAssingmentById(id);
        return optional.orElseThrow(
                () -> new AssignmentNotFoundException( "Assignment with: " + id + " id was not found.")
        );
    }

    // (UPDATE) Put Assignment by Id  /api/assignments/{id}
    @PutMapping("/api/assignments/{id}")
    public ResponseEntity<Object> updateAssignment
        (@RequestBody Assignment assignment, @PathVariable Long id) {

        Optional<Assignment> optional = assignmentRepository.findAssingmentById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        assignment.setId(id);

        assignmentRepository.save(assignment);

        return ResponseEntity.noContent().build();
    }

    // Post (NEW) Assignment /api/assignments
    @PostMapping("/api/assignments")
    public ResponseEntity<Object> createAssignment(@RequestBody Assignment assignment) {
        Assignment savedAssignment  = assignmentRepository.save(assignment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAssignment.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    // DELETE an Assignment by Id  /api/assignments/{id}
    @DeleteMapping("api/assignments/{id}")
    public void deleteAssignmentById(@PathVariable Long id) {
        assignmentRepository.deleteById(id);
    }

}

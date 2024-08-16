package org.example.project;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.project.dto.ProjectRequest;
import org.example.project.dto.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/project")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Project API", description = "")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getById(@PathVariable Integer id) {
        ProjectResponse project = null;
        try {
            project = projectService.getById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<ProjectResponse>> all() {
        return ResponseEntity.ok(projectService.all());
    }

    @PostMapping("/public")
    public ResponseEntity<ProjectResponse> createPublicProject(@RequestBody ProjectRequest projectRequest, @RequestHeader HttpHeaders headers) {

        return ResponseEntity.ok(projectService.createPublic(projectRequest, headers));
    }

    @PostMapping("/private")
    public ResponseEntity<ProjectResponse> createPrivateProject(@RequestBody ProjectRequest projectRequest, @RequestHeader HttpHeaders headers) {

        return ResponseEntity.ok(projectService.createPrivate(projectRequest, headers));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@RequestBody ProjectRequest projectRequest, @PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        ProjectResponse project = null;
        try {
            project = projectService.update(id, projectRequest, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{projectId}/addMember/{userId}")
    public ResponseEntity<String> addMember(@PathVariable Integer projectId, @PathVariable Integer userId) {
        try {
            projectService.addMember(userId, projectId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Member now participates project");
    }

    @PutMapping("/{projectId}/removeMember/{userId}")
    public ResponseEntity<String> removeMember(@PathVariable Integer projectId, @PathVariable Integer userId) {
        try {
            projectService.removeMember(userId, projectId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Member removed from project");
    }

    @PutMapping("/{projectId}/addFollower/{userId}")
    public ResponseEntity<String> addFollower(@PathVariable Integer projectId, @PathVariable Integer userId) {
        try {
            projectService.addFollower(userId, projectId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Member followed on project");
    }

    @PutMapping("/{projectId}/removeFollower/{userId}")
    public ResponseEntity<String> removeFollower(@PathVariable Integer projectId, @PathVariable Integer userId) {
        ProjectResponse project = null;
        try {
            projectService.removeFollower(userId, projectId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Member discard subscription on project");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        try {
            projectService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

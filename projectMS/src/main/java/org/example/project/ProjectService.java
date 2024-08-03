package org.example.project;

import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.example.client.UserServiceClient;
import org.example.mappers.ProjectMapper;
import org.example.project.dto.ProjectRequest;
import org.example.project.dto.ProjectResponse;
import org.example.project.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserServiceClient userServiceClient;

    public void addMember(Integer userId, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        project.getMemberIds().add(userId);

        projectRepository.save(project);
    }

    public void removeMember(Integer userId, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        project.getMemberIds().removeIf(e -> e.equals(userId));

        projectRepository.save(project);
    }

    public void addFollower(Integer userId, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        project.getFollowerIds().add(userId);

        projectRepository.save(project);
    }

    public void removeFollower(Integer userId, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        project.getFollowerIds().removeIf(e -> e.equals(userId));

        projectRepository.save(project);
    }

    public Set<ProjectResponse> all() {
        Set<Project> projects = new HashSet<>(projectRepository.findAll());
        return ProjectMapper.INSTANCE.toDtos(projects);
    }

    public Set<ProjectResponse> allForUser(Integer memberId) {
        Set<Project> projects = projectRepository.findByMemberIdsContaining(memberId);

        return ProjectMapper.INSTANCE.toDtos(projects);
    }

    public ProjectResponse getById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse create(ProjectRequest request, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        Project project = new Project();
        project.setName(request.getName());
        project.setLink(request.getLink());
        project.setDescription(request.getDescription());
        assert user != null;
        project.setCreatedBy(user.getId());
        project.setCreatedAt(OffsetDateTime.now());
        projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse update(Integer id, ProjectRequest request, HttpHeaders headers) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project with id: " + id + " not found"));
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        project.setName(request.getName());
        project.setLink(request.getLink());
        project.setDescription(request.getDescription());
        assert user != null;
        project.setUpdatedBy(user.getId());
        project.setUpdatedAt(OffsetDateTime.now());
        projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }
}

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
        Project project = findProjectById(projectId);

        project.getMemberIds().add(userId);

        projectRepository.save(project);
    }

    public void removeMember(Integer userId, Integer projectId) {
        Project project = findProjectById(projectId);

        project.getMemberIds().removeIf(e -> e.equals(userId));

        projectRepository.save(project);
    }

    public void addFollower(Integer userId, Integer projectId) {
        Project project = findProjectById(projectId);

        project.getFollowerIds().add(userId);

        projectRepository.save(project);
    }

    public void removeFollower(Integer userId, Integer projectId) {
        Project project = findProjectById(projectId);
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
        Project project = findProjectById(id);
        return ProjectMapper.INSTANCE.toDto(project);
    }

    private Project findProjectById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with id: " + id + " found"));
    }

    public ProjectResponse copyProject(Integer projectId, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        Project publicProject = findProjectById(projectId);
        Project project = new Project();
        project.setName(publicProject.getName());
        project.setLink(publicProject.getLink());
        project.setDescription(publicProject.getDescription());
        project.setPublic(false);
        assert user != null;
        project.setCreatedBy(user.getId());
        project.setCreatedAt(OffsetDateTime.now());
        projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse createPrivate(ProjectRequest request, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        Project project = new Project();
        project.setName(request.getName());
        project.setLink(request.getLink());
        project.setDescription(request.getDescription());
        project.setPublic(false);
        assert user != null;
        project.setCreatedBy(user.getId());
        project.setCreatedAt(OffsetDateTime.now());
        projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse createPublic(ProjectRequest request, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        Project project = new Project();
        project.setName(request.getName());
        project.setLink(request.getLink());
        project.setDescription(request.getDescription());
        project.setPublic(true);
        assert user != null;
        project.setCreatedBy(user.getId());
        project.setCreatedAt(OffsetDateTime.now());
        projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse update(Integer id, ProjectRequest request, HttpHeaders headers) {
        Project project = findProjectById(id);
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

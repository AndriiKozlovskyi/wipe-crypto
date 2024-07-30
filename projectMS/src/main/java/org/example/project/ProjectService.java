package org.example.project;

import jakarta.persistence.EntityNotFoundException;
import org.example.mappers.ProjectMapper;
import org.example.project.dto.ProjectRequest;
import org.example.project.dto.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    public void addMember(Integer userId, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        project.getMembersIds().add(userId);

        //save to
        projectRepository.save(project);
    }

    public void removeMember(Integer userId, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        project.getMembersIds().removeIf(e -> e.equals(userId));

        projectRepository.save(project);
    }

    public void addFollower(Integer userId, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        project.getFollowersIds().add(userId);

        projectRepository.save(project);
    }

    public void removeFollower(Integer userId, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        project.getFollowersIds().removeIf(e -> e.equals(userId));

        projectRepository.save(project);
    }

    public Set<ProjectResponse> all() {
        Set<Project> projects = new HashSet<>(projectRepository.findAll());
        return ProjectMapper.INSTANCE.toDtos(projects);
    }

    public ProjectResponse getById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse create(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setLink(request.getLink());
        project.setDescription(request.getDescription());

        projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse update(Integer id, ProjectRequest request) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project with id: " + id + " not found"));
        project.setName(request.getName());
        project.setLink(request.getLink());
        project.setDescription(request.getDescription());

        projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDto(project);
    }

    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }
}

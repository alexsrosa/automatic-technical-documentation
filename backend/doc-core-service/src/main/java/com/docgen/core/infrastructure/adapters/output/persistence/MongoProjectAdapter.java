package com.docgen.core.infrastructure.adapters.output.persistence;

import com.docgen.core.domain.model.Project;
import com.docgen.core.domain.ports.output.ProjectRepositoryPort;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.ProjectEntity;
import com.docgen.core.infrastructure.adapters.output.persistence.mapper.ProjectMapper;
import com.docgen.core.infrastructure.adapters.output.persistence.repository.SpringDataProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MongoProjectAdapter implements ProjectRepositoryPort {
    
    private final SpringDataProjectRepository repository;
    private final ProjectMapper mapper;

    @Override
    public Project save(Project project) {
        ProjectEntity entity = mapper.toEntity(project);
        ProjectEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Project> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Project> findByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

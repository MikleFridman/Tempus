package il.fridman.tempus.company.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.company.entity.Position;
import il.fridman.tempus.company.repository.PositionRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class PositionService implements EntityService<Position> {

    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public Position save(Position entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        return positionRepository.save(entity);
    }

    @Override
    @Transactional
    public Position update(Position entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        return positionRepository.save(entity);
    }

    @Override
    public Position getById(Long id) {
        return positionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Position> getAll() {
        return positionRepository.findAll();
    }
}

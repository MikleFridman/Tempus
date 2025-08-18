package il.fridman.tempus.company.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.company.entity.Location;
import il.fridman.tempus.company.repository.LocationRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class LocationService implements EntityService<Location> {

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Location save(Location entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Location entity cannot be null.");
        }
        return locationRepository.save(entity);
    }

    @Override
    @Transactional
    public Location update(Location entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Location entity cannot be null.");
        }
        return locationRepository.save(entity);
    }

    @Override
    public Location getById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Location> getAll() {
        return locationRepository.findAll();
    }
}

package il.fridman.tempus.salary.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.salary.entity.Deduction;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class DeductionService implements EntityService<Deduction> {

    @Override
    public Deduction save(Deduction entity) {
        return null;
    }

    @Override
    public Deduction update(Deduction entity) {
        return null;
    }

    @Override
    public Deduction getById(Long id) {
        return null;
    }

    @Override
    public List<Deduction> getAll() {
        return List.of();
    }
}

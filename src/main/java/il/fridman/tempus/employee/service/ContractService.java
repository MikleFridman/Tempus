package il.fridman.tempus.employee.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.employee.dto.ContractDTO;
import il.fridman.tempus.employee.entity.Contract;
import il.fridman.tempus.employee.mapper.ContractMapper;
import il.fridman.tempus.employee.repository.ContractRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class ContractService implements EntityService<Contract> {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    @Override
    @Transactional
    public Contract save(Contract entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Contract entity cannot be null.");
        }
        return contractRepository.save(entity);
    }

    @Override
    @Transactional
    public Contract update(Contract entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Contract entity cannot be null.");
        }
        return contractRepository.save(entity);
    }

    @Override
    public Contract getById(Long id) {
        return contractRepository.findById(id).orElse(null);
    }

    @Override
    public List<Contract> getAll() {
        return contractRepository.findAll();
    }

    // DTO methods
    public ContractDTO getDTO(Contract entity) {
        return entity != null ? contractMapper.toDTO(entity) : new ContractDTO();
    }
}

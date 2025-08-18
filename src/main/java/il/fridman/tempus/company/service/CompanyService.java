package il.fridman.tempus.company.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.company.entity.Company;
import il.fridman.tempus.company.repository.CompanyRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class CompanyService implements EntityService<Company> {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public Company save(Company entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Company entity cannot be null.");
        }
        return companyRepository.save(entity);
    }

    @Override
    @Transactional
    public Company update(Company entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Company entity cannot be null.");
        }
        return companyRepository.save(entity);
    }

    @Override
    public Company getById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company> getAll() {
        return companyRepository.findAll();
    }
}

package il.fridman.tempus.company.controller;

import il.fridman.tempus.company.entity.Company;
import il.fridman.tempus.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("all")
    public List<Company> getAllCompanies() {
        return companyService.getAll();
    }

    @GetMapping("get")
    public ResponseEntity<Company> getCompanyById(@RequestParam Long id) {
        Company company = companyService.getById(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }

    @PostMapping("new")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        Company savedCompany = companyService.save(company);
        if (savedCompany == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedCompany);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteCompany(@RequestParam Long id) {
        boolean deleted = companyService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

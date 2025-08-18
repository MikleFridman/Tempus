package il.fridman.tempus.employee.controller;

import il.fridman.tempus.employee.dto.ContractDTO;
import il.fridman.tempus.employee.entity.Contract;
import il.fridman.tempus.employee.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @GetMapping("all")
    public List<Contract> getAllContracts() {
        return contractService.getAll();
    }

    @GetMapping("get")
    public ResponseEntity<ContractDTO> getContractById(@RequestParam Long id) {
        Contract contract = contractService.getById(id);
        if (contract == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contractService.getDTO(contract));
    }

    @PostMapping("new")
    public ResponseEntity<ContractDTO> createContract(@Valid @RequestBody Contract contract) {
        Contract savedContract = contractService.save(contract);
        if (savedContract == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(contractService.getDTO(savedContract));
    }
}

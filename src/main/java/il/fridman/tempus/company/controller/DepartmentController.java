package il.fridman.tempus.company.controller;

import il.fridman.tempus.company.entity.Department;
import il.fridman.tempus.company.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("all")
    public List<Department> getAllDepartments() {
        return departmentService.getAll();
    }

    @GetMapping("get")
    public ResponseEntity<Department> getDepartmentById(@RequestParam Long id) {
        Department department = departmentService.getById(id);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department);
    }

    @PostMapping ("new")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {
        Department savedDepartment = departmentService.save(department);
        if (savedDepartment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedDepartment);
    }
}

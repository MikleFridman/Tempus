package il.fridman.tempus.salary.controller;

import il.fridman.tempus.salary.entity.Earning;
import il.fridman.tempus.salary.service.EarningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/earnings")
@RequiredArgsConstructor
public class EarningController {

    private final EarningService earningService;

    @PutMapping("/new")
    public ResponseEntity<Earning> save(@Valid @RequestBody Earning earning) {
        if (earning == null) {
            return ResponseEntity.badRequest().build();
        }
        Earning savedEarning = earningService.save(earning);
        return ResponseEntity.ok(savedEarning);
    }
}

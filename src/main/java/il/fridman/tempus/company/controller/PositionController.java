package il.fridman.tempus.company.controller;

import il.fridman.tempus.company.entity.Position;
import il.fridman.tempus.company.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping("all")
    public List<Position> getAllPositions() {
        return positionService.getAll();
    }
    @GetMapping("get")
    public ResponseEntity<Position> getPositionById(@RequestParam Long id) {
        Position position = positionService.getById(id);
        if (position == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(position);
    }

    @PostMapping("new")
    public ResponseEntity<Position> createPosition(@Valid @RequestBody Position position) {
        Position savedPosition = positionService.save(position);
        if (savedPosition == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedPosition);
    }
}

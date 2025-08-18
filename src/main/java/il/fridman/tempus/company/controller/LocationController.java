package il.fridman.tempus.company.controller;

import il.fridman.tempus.company.entity.Location;
import il.fridman.tempus.company.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("all")
    public List<Location> getAllLocations() {
        return locationService.getAll();
    }

    @GetMapping("get")
    public ResponseEntity<Location> getLocationById(Long id) {
        Location location = locationService.getById(id);
        if (location == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(location);
    }

    @GetMapping("new")
    public ResponseEntity<Location> saveLocation(@Valid @RequestBody Location location) {
        Location savedLocation = locationService.save(location);
        if (savedLocation == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedLocation);
    }
}

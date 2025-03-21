package com.spring.example.location.controller;
import com.spring.example.location.model.Location;
import com.spring.example.location.model.Weather;
import com.spring.example.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService service;

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String name) {
        return service.redirectRequestWeather(name);
    }

    @GetMapping("/{name}")
    public Optional<Location> getLocation(@PathVariable String name) {
        return service.getLocation(name);
    }

    @GetMapping
    public Iterable<Location> getLocations() {
        return service.getLocations();
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return service.save(location);
    }

    @PutMapping
    public Location update(@RequestParam String name, @RequestBody Location newLocation) {
        return service.update(name, newLocation);
    }

    @DeleteMapping
    public void delete(@RequestParam String name) {
        service.delete(name);
    }

}

package com.spring.example.location.service;

import com.spring.example.location.model.Location;
import com.spring.example.location.model.Weather;
import com.spring.example.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${weather.info}")
    private String weatherUrl;
    @Autowired
    private LocationRepository repository;

    public Weather redirectRequestWeather(String name) {
        Optional<Location> location = repository.findByName(name);
        if (location.isPresent()) {
            Location loc = location.get();
            String url = String.format("http://%s/weather?lat=%s&lon=%s", weatherUrl,
                    loc.getLatitude(), loc.getLongitude());
            return restTemplate.getForObject(url, Weather.class);
        } else {
            throw new RuntimeException("Location not found:" + location);
        }
    }

    public Optional<Location> getLocation(String name) {
        return repository.findByName(name);
    }

    public Iterable<Location> getLocations() {
        return repository.findAll();
    }

    public Location save(Location location) {
        return repository.save(location);
    }
    public Location update(String name, Location newLocation) {
        Location loc = repository.findByName(name).get();
        loc.setLatitude(newLocation.getLatitude());
        loc.setLongitude(newLocation.getLongitude());
        loc.setName(newLocation.getName());
        return repository.save(loc);
    }

    public void delete(String name) {
        repository.delete(repository.findByName(name).get());
    }
}

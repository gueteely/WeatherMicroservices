package com.spring.example.weather.controller;
import com.spring.example.weather.model.Main;
import com.spring.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


@RestController
public class WeatherController {
    @Autowired
    private WeatherService service;

    @GetMapping("/weather")
    @Cacheable(value = "weatherCache", key = "#lat + '-' + #lon")
    public Main getWeather(@RequestParam String lat, @RequestParam String lon) {
        return service.getWeather(lat, lon);
    }
}
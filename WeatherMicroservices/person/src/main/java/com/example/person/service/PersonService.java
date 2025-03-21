package com.example.person.service;

import com.example.person.model.Person;
import com.example.person.model.Weather;
import com.example.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${location.info}")
    private String locationUrl;

    public ResponseEntity<Weather> getWeather(int id) {
        if (repository.existsById(id)) {
            String location = repository.findById(id).get().getLocation();
            String url = String.format("http://%s/location/weather?name=%s", locationUrl, location);
            Weather weather = restTemplate.getForObject(url, Weather.class);
            return new ResponseEntity(weather, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }

    public ResponseEntity<Person> save(Person person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }

    public ResponseEntity<Person> update(int id, Person person) {
        Person updatedPerson = repository.findById(id).get();
        updatedPerson.setBirthday(person.getBirthday());
        updatedPerson.setLocation(person.getLocation());
        updatedPerson.setSurname(person.getSurname());
        updatedPerson.setLastname(person.getLastname());
        updatedPerson.setFirstname(person.getFirstname());
        return new ResponseEntity<>(repository.save(updatedPerson), HttpStatus.OK);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}

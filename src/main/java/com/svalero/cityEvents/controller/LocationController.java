package com.svalero.cityEvents.controller;

import com.svalero.cityEvents.domain.Location;
import com.svalero.cityEvents.exception.ErrorResponse;
import com.svalero.cityEvents.exception.LocationNotFoundException;
import com.svalero.cityEvents.repository.LocationRepository;
import com.svalero.cityEvents.service.LocationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getALL(@RequestParam(value = "category", defaultValue = "") String category) { //indicamos que queremos que filtre por category la busqueda con el Request param
        List<Location> allLocations;

        if (!category.isEmpty()) {
            allLocations = locationService.findByCategory(category);
        } else {
            allLocations = locationService.findAll();
        }

        return ResponseEntity.ok(allLocations);
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable long id) throws LocationNotFoundException {
        Location location = locationService.findById(id);
        return ResponseEntity.ok(location);
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> addLocation(@RequestBody Location location) { //me pasan el juego que quiero añadir en el body de la llamada
        Location newLocation = locationService.add(location);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED); //nos devuelve el juego posteado cuando se crea1
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> modifyLocation(@PathVariable long id, @RequestBody Location location) throws LocationNotFoundException { //usamos el path variable para recoger el id del elemento que queremos modificar en el endpoint
       Location newLocation= locationService.modify(id, location);
//       return new ResponseEntity<>(newLocation, HttpStatus.OK);   Es identico a la siguiente linea
        return ResponseEntity.ok(newLocation);
    } //cuando se modifica la localización nos la devuelve

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable long id) throws LocationNotFoundException {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(LocationNotFoundException lnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, "not-found", "The game does not exist");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

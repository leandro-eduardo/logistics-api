package com.springboot.logistics.api.controller;

import java.net.URI;

import javax.validation.Valid;

import com.springboot.logistics.domain.model.City;
import com.springboot.logistics.domain.service.CityService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {

    private CityService cityService;

    @GetMapping
    public Page<City> list(Pageable pageable) {
        return cityService.list(pageable);
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<City> find(@PathVariable Long cityId) {
        return ResponseEntity.ok(cityService.find(cityId));
    }

    @PostMapping
    public ResponseEntity<City> insert(@Valid @RequestBody City city) {
        cityService.insert(city);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cityId}").buildAndExpand(city.getId())
                .toUri();
        return ResponseEntity.created(uri).body(city);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<City> update(@Valid @PathVariable Long cityId, @RequestBody City city) {
        City savedCity = cityService.update(cityId, city);
        return ResponseEntity.ok(savedCity);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> delete(@PathVariable Long cityId) {
        cityService.delete(cityId);
        return ResponseEntity.noContent().build();
    }

}
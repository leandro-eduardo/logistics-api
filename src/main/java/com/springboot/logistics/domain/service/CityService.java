package com.springboot.logistics.domain.service;

import javax.transaction.Transactional;

import com.springboot.logistics.domain.model.City;
import com.springboot.logistics.domain.repository.CityRepository;
import com.springboot.logistics.domain.service.exceptions.CityNotFoundException;
import com.springboot.logistics.domain.service.exceptions.RegisteredCityException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CityService {

    private CityRepository cidadeRepository;

    public Page<City> list(Pageable pageable) {
        return cidadeRepository.findAll(pageable);
    }

    public City find(Long cityId) {
        return cidadeRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException("city with ID " + cityId + " not found"));
    }

    @Transactional
    public void insert(City city) {
        boolean isCityRegistered = cidadeRepository.findByName(city.getName()).isPresent();
        if (isCityRegistered) {
            throw new RegisteredCityException("there is already a city registered with this name");
        }
        cidadeRepository.save(city);
    }

    @Transactional
    public City update(Long cityId, City city) {
        City savedCity = find(cityId);
        BeanUtils.copyProperties(city, savedCity);
        savedCity.setId(cityId);
        return cidadeRepository.save(savedCity);
    }

    @Transactional
    public void delete(Long cityId) {
        City savedCity = find(cityId);
        cidadeRepository.deleteById(savedCity.getId());
    }

}
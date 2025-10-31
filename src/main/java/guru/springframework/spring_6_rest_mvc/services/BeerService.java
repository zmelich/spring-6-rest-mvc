package guru.springframework.spring_6_rest_mvc.services;

import guru.springframework.spring_6_rest_mvc.model.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
}

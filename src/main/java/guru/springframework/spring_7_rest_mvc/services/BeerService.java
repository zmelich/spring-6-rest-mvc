package guru.springframework.spring_7_rest_mvc.services;

import guru.springframework.spring_7_rest_mvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    //Beer getBeerById(UUID id);
    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer);

    Boolean deleteBeerById(UUID id);

    void patchBeerById(UUID id, BeerDTO beer);
}

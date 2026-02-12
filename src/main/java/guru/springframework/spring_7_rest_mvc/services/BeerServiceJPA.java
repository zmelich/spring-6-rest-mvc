package guru.springframework.spring_7_rest_mvc.services;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_7_rest_mvc.mappers.BeerMapper;
import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import guru.springframework.spring_7_rest_mvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        //return List.of();
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id)
    {
        //return Optional.empty();
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {

        return beerMapper.beerToBeerDto(beerRepository
                .save(beerMapper.beerDtoToBeer(beer)));


    }

    @Override
    public void updateBeerById(UUID id, BeerDTO beer) {

    }

    @Override
    public void deleteBeerById(UUID id) {

    }

    @Override
    public void patchBeerById(UUID id, BeerDTO beer) {

    }
}

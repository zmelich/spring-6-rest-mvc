package guru.springframework.spring_7_rest_mvc.services;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_7_rest_mvc.entities.Beer;
import guru.springframework.spring_7_rest_mvc.mappers.BeerMapper;
import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import guru.springframework.spring_7_rest_mvc.model.BeerStyle;
import guru.springframework.spring_7_rest_mvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventoryOnHand) {

        List<Beer> beerList;

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerList = listBeersByName(beerName);
        }
        else{
            if (!StringUtils.hasText(beerName) && beerStyle != null)
            {
                beerList = listBeersByStyle(beerStyle);
            }
            else
            {
                if (StringUtils.hasText(beerName) && beerStyle != null)
                {
                    beerList = listBeersByNameAndStyle(beerName, beerStyle);
                }
                else{
                    beerList =  beerRepository.findAll();
                }
            }
        }

        if (showInventoryOnHand != null && !showInventoryOnHand)
        {
            beerList.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerList
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());

        /* Original - before query params
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());*/
    }

    private List<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle) {
        return new ArrayList<>(beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle));
    }

    List<Beer> listBeersByName(String beerName){
        return new ArrayList<>(beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%"));
    }

    List<Beer> listBeersByStyle(BeerStyle beerStyle)
    {
        return new ArrayList<>(beerRepository.findAllByBeerStyle(beerStyle));
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
    public Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer) {

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        /*beerRepository.findById(id).ifPresent(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            beerRepository.save(foundBeer);
        });*/

        beerRepository.findById(id).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID id) {
        if (beerRepository.existsById(id))
        {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID id, BeerDTO beerDTO) {

        /*
        beerRepository.findById(id).ifPresent(foundBeer -> {
            if (StringUtils.hasText(beerDTO.getBeerName())) {
                foundBeer.setBeerName(beerDTO.getBeerName());
            }
            ;
            if (beerDTO.getBeerStyle() != null) {
                foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            }
            ;
            if (beerDTO.getPrice() != null) {
                foundBeer.setPrice(beerDTO.getPrice());
            }
            ;
            if (beerDTO.getQuantityOnHand() != null) {
                foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            }
            if (StringUtils.hasText(beerDTO.getUpc())) {
                foundBeer.setUpc(beerDTO.getUpc());
            }
            beerRepository.save(foundBeer);
        });*/

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(id).ifPresentOrElse(foundBeer -> {
            if(StringUtils.hasText(beerDTO.getBeerName()))
            {
              foundBeer.setBeerName(beerDTO.getBeerName());
            };
            if(beerDTO.getBeerStyle() != null)
            {
              foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            };
            if(beerDTO.getPrice() != null)
            {
                foundBeer.setPrice(beerDTO.getPrice());
            };
            if(beerDTO.getQuantityOnHand() != null)
            {
                foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            }
            if(StringUtils.hasText(beerDTO.getUpc()))
            {
                foundBeer.setUpc(beerDTO.getUpc());
            }
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

}

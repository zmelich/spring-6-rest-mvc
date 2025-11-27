package guru.springframework.spring_6_rest_mvc.controller;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_6_rest_mvc.model.Beer;
import guru.springframework.spring_6_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
//@RequestMapping("/api/v1/beer")
//@Controller
@RestController
public class BeerController {

    public static final String BEER_PATH ="/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    //@PatchMapping(value="{beerId}")
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID id, @RequestBody Beer beer){

        beerService.patchBeerById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    //@DeleteMapping(value="{beerId}")
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID id)
    {
        beerService.deleteBeerById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //@PutMapping("{beerId}")
    //@PutMapping(value = "{beerId}")
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID id, @RequestBody Beer beer)
    {
        beerService.updateBeerById(id,beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //@PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@RequestBody Beer beer){

        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        //headers.add("Location","/api/v1/beer/"+savedBeer.getId().toString());
        headers.add("Location",BEER_PATH+"/"+savedBeer.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);

    }

    //@RequestMapping("/api/v1/beer")
    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(BEER_PATH)
    public List<Beer> listBeers()
    {
        return beerService.listBeers();
    }

    //@RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID id){
        log.debug("Get Beer by Id - In controller - 1234");

        return beerService.getBeerById(id);
    }



}

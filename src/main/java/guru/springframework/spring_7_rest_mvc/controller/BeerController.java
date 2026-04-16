package guru.springframework.spring_7_rest_mvc.controller;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import guru.springframework.spring_7_rest_mvc.model.BeerStyle;
import guru.springframework.spring_7_rest_mvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer){


        if (beerService.patchBeerById(id, beer).isEmpty())
        {
            throw new NotFoundException();
        }
        //beerService.patchBeerById(id, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    //@DeleteMapping(value="{beerId}")
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID id)
    {

        if (!beerService.deleteBeerById(id)){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //@PutMapping("{beerId}")
    //@PutMapping(value = "{beerId}")
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID id,@Validated @RequestBody BeerDTO beer)
    {
        if (beerService.updateBeerById(id,beer).isEmpty())
        {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //@PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beer){

        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        //headers.add("Location","/api/v1/beer/"+savedBeer.getId().toString());
        headers.add("Location",BEER_PATH+"/"+savedBeer.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);

    }

    //@RequestMapping("/api/v1/beer")
    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers(@RequestParam (required = false) String beerName, @RequestParam (required = false) BeerStyle beerStyle)
    {
        return beerService.listBeers(beerName, beerStyle);
    }

    //@RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    @GetMapping(BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID id){
        log.debug("Get Beer by Id - In controller - 1234");

        //return beerService.getBeerById(id);
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }



}

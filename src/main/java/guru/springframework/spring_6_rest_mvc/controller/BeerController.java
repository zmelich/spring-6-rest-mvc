package guru.springframework.spring_6_rest_mvc.controller;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_6_rest_mvc.model.Beer;
import guru.springframework.spring_6_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/beer")
//@Controller
@RestController
public class BeerController {
    private final BeerService beerService;

    //@RequestMapping("/api/v1/beer")
    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers()
    {
        return beerService.listBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID id){
        log.debug("Get Beer by Id - In controller");

        return beerService.getBeerById(id);
    }

}

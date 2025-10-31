package guru.springframework.controller;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.services.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

}

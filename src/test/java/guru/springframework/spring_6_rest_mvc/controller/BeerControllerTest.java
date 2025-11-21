package guru.springframework.spring_6_rest_mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring_6_rest_mvc.controller.BeerController;
import guru.springframework.spring_6_rest_mvc.model.Beer;
import guru.springframework.spring_6_rest_mvc.services.BeerService;
import guru.springframework.spring_6_rest_mvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    //@Autowired
    //BeerController beerController;

    @Autowired
    MockMvc mockMVC;

    @Autowired
    ObjectMapper objectMapper;

    //@MockBean -- deprecated
    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void testCreateNewBeer() throws JsonProcessingException {
        //ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.findAndRegisterModules();

        Beer beer = beerServiceImpl.listBeers().get(0);

        //This is the least preferred way
        System.out.println(objectMapper.writeValueAsString(beer));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMVC.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }


    @Test
    void testGetBeerById() throws Exception{
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMVC.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
      //  System.out.println(beerController.getBeerById(UUID.randomUUID()));

    }
}
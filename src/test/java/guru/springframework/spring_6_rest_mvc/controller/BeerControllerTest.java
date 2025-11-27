package guru.springframework.spring_6_rest_mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring_6_rest_mvc.controller.BeerController;
import guru.springframework.spring_6_rest_mvc.model.Beer;
import guru.springframework.spring_6_rest_mvc.services.BeerService;
import guru.springframework.spring_6_rest_mvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    //BeerServiceImpl beerServiceImpl = new BeerServiceImpl();
    BeerServiceImpl beerServiceImpl ;

    @Captor
    ArgumentCaptor<UUID> beerIdArgCaptor;

    @Captor
    ArgumentCaptor<Beer> beerObjectCaptor;

    @BeforeEach
    void setUp(){
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeersByIdNotFound() throws Exception{

        //given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMVC.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPatchBeer() throws Exception
    {
        Beer beer = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName","New Beer Name");

        //mockMVC.perform(patch(BeerController.BEER_PATH +"/"+beer.getId())
        mockMVC.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(beerIdArgCaptor.capture(),beerObjectCaptor.capture());

        assertThat(beer.getId()).isEqualTo(beerIdArgCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerObjectCaptor.getValue().getBeerName());

    }

    @Test
    void testDeleteBeer() throws Exception
    {
        Beer beer = beerServiceImpl.listBeers().get(0);

        //mockMVC.perform(delete(BeerController.BEER_PATH+"/"+beer.getId())
        mockMVC.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        // verify(beerService).deleteBeerById(uuidArgumentCaptor.capture());

        verify(beerService).deleteBeerById(beerIdArgCaptor.capture());
        assertThat(beer.getId()).isEqualTo(beerIdArgCaptor.getValue());
    }


    @Test
    void testUpdateExistingBeer() throws Exception{
        Beer beer = beerServiceImpl.listBeers().get(0);

        //mockMVC.perform(put(BeerController.BEER_PATH+"/" + beer.getId())
        mockMVC.perform(put(BeerController.BEER_PATH_ID,beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        //verify(beerService).updateBeerById(any(UUID.class),any(Beer.class));
        verify(beerService).updateBeerById(beerIdArgCaptor.capture(),beerObjectCaptor.capture());

    }

    @Test
    void testCreateNewBeer() throws Exception {

        Beer beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMVC.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMVC.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }


    @Test
    void testGetBeerById() throws Exception{
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        //given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);
        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        //mockMVC.perform(get(BeerController.BEER_PATH+"/" + testBeer.getId())
        mockMVC.perform(get(BeerController.BEER_PATH_ID,testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
      //  System.out.println(beerController.getBeerById(UUID.randomUUID()));

    }
}
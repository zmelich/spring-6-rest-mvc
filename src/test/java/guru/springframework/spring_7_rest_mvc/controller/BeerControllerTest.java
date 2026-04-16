package guru.springframework.spring_7_rest_mvc.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
//Reimporting ObjectMapper
import guru.springframework.spring_7_rest_mvc.model.BeerStyle;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import guru.springframework.spring_7_rest_mvc.services.BeerService;
import guru.springframework.spring_7_rest_mvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//Reimport WebMvcTest
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//@SpringBootTest

//@ExtendWith added for newer version of Spring Boot. Without that intelliJ would not do things like autocomplete for Mockito
@WebMvcTest(BeerController.class)
@ExtendWith(MockitoExtension.class)
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
    ArgumentCaptor<BeerDTO> beerObjectCaptor;

    @BeforeEach
    void setUp(){
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testUpdateBeerByIdNullUpc() throws Exception
    {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        BeerDTO updatedBeerNullName = BeerDTO.builder()
                .version(null)
                .id(null)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .price(new BigDecimal(2.90))
                .quantityOnHand(beer.getQuantityOnHand())
                .build();


        MvcResult mvcResult = mockMVC.perform(put(BeerController.BEER_PATH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeerNullName)))
                .andExpect(jsonPath("$.length()",is(2)))
                .andExpect(status().isBadRequest()).andReturn();

        /*We need to comment this code. We don't need to and we cannot verify that the beerService.updateBeerById() is invoked
        because it won't happen due to the failed validation!
        The validation was done in the Controller before the app would went to the service (which is good!)*/
        //verify(beerService).updateBeerById(beerIdArgCaptor.capture(),beerObjectCaptor.capture());

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateBeerByIdNegativePrice() throws Exception
    {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        BeerDTO updatedBeerNullName = BeerDTO.builder()
                .version(null)
                .id(null)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .price(new BigDecimal(-2.43))
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();


        MvcResult mvcResult = mockMVC.perform(put(BeerController.BEER_PATH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeerNullName)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        /*We need to comment this code. We don't need to and we cannot verify that the beerService.updateBeerById() is invoked
        because it won't happen due to the failed validation!
        The validation was done in the Controller before the app would went to the service (which is good!)*/
        //verify(beerService).updateBeerById(beerIdArgCaptor.capture(),beerObjectCaptor.capture());

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateBeerByIdZeroPrice() throws Exception
    {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        BeerDTO updatedBeerNullName = BeerDTO.builder()
                .version(null)
                .id(null)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .price(new BigDecimal(0))
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();


        MvcResult mvcResult = mockMVC.perform(put(BeerController.BEER_PATH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeerNullName)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        /*We need to comment this code. We don't need to and we cannot verify that the beerService.updateBeerById() is invoked
        because it won't happen due to the failed validation!
        The validation was done in the Controller before the app would went to the service (which is good!)*/
        //verify(beerService).updateBeerById(beerIdArgCaptor.capture(),beerObjectCaptor.capture());

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateBeerByIdNullPrice() throws Exception
    {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        BeerDTO updatedBeerNullName = BeerDTO.builder()
                .version(null)
                .id(null)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();


        MvcResult mvcResult = mockMVC.perform(put(BeerController.BEER_PATH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeerNullName)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        /*We need to comment this code. We don't need to and we cannot verify that the beerService.updateBeerById() is invoked
        because it won't happen due to the failed validation!
        The validation was done in the Controller before the app would went to the service (which is good!)*/
        //verify(beerService).updateBeerById(beerIdArgCaptor.capture(),beerObjectCaptor.capture());

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateBeerByIdNullBeerStyle() throws Exception
    {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        BeerDTO updatedBeerNullName = BeerDTO.builder()
                .version(null)
                .id(null)
                .beerName(beer.getBeerName())
                .price(beer.getPrice())
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();


        MvcResult mvcResult = mockMVC.perform(put(BeerController.BEER_PATH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeerNullName)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        /*We need to comment this code. We don't need to and we cannot verify that the beerService.updateBeerById() is invoked
        because it won't happen due to the failed validation!
        The validation was done in the Controller before the app would went to the service (which is good!)*/
        //verify(beerService).updateBeerById(beerIdArgCaptor.capture(),beerObjectCaptor.capture());

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testUpdateBeerByIdNullBeerName() throws Exception
    {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        BeerDTO updatedBeerNullName = BeerDTO.builder()
                .version(null)
                .id(null)
                .beerStyle(beer.getBeerStyle())
                .price(beer.getPrice())
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();


        MvcResult mvcResult = mockMVC.perform(put(BeerController.BEER_PATH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeerNullName)))
                .andExpect(jsonPath("$.length()",is(2)))
                .andExpect(status().isBadRequest()).andReturn();

        /*We need to comment this code. We don't need to and we cannot verify that the beerService.updateBeerById() is invoked
        because it won't happen due to the failed validation!
        The validation was done in the Controller before the app would went to the service (which is good!)*/
        //verify(beerService).updateBeerById(beerIdArgCaptor.capture(),beerObjectCaptor.capture());

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testCreateNewBeerNullBeerStyle() throws Exception
    {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Test BeerName")
                .upc("upc")
                .price(new BigDecimal(11.25))
                .id(null)
                .version(null)
                .build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false).get(1));

        MvcResult mvcResult = mockMVC.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateNewBeerNullBeerUpc() throws Exception
    {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Test BeerName")
                .beerStyle(BeerStyle.LAGER)
                .price(new BigDecimal(11.25))
                .id(null)
                .version(null)
                .build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false).get(1));

        MvcResult mvcResult = mockMVC.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(jsonPath("$.length()",is(2)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateNewBeerNegativeBeerPrice() throws Exception
    {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Test BeerName")
                .beerStyle(BeerStyle.LAGER)
                .price(new BigDecimal(-2.00))
                .upc("upc")
                .id(null)
                .version(null)
                .build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false).get(1));

        MvcResult mvcResult = mockMVC.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateNewBeerZeroBeerPrice() throws Exception
    {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Test BeerName")
                .beerStyle(BeerStyle.LAGER)
                .price(new BigDecimal(0))
                .upc("upc")
                .id(null)
                .version(null)
                .build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false).get(1));

        MvcResult mvcResult = mockMVC.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateNewBeerNullBeerPrice() throws Exception
    {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Test BeerName")
                .beerStyle(BeerStyle.LAGER)
                .upc("upc")
                .id(null)
                .version(null)
                .build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false).get(1));

        MvcResult mvcResult = mockMVC.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception{

        //BeerDTO beerDTO = BeerDTO.builder().build();
        BeerDTO beerDTO = BeerDTO.builder()
                .price(new BigDecimal(8.21))
                .beerStyle(BeerStyle.PILSNER)
                .upc("upc")
                .id(null)
                .version(null)
                .build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false).get(1));

        MvcResult mvcResult = mockMVC.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(jsonPath("$.length()",is(2)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

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
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.patchBeerById(any(UUID.class),any(BeerDTO.class))).willReturn(Optional.of(beer));

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
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.deleteBeerById(any())).willReturn(true);

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
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

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

        BeerDTO beer = beerServiceImpl.listBeers(null, null, false).get(0);
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false).get(1));

        mockMVC.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers(any(), any(), any())).willReturn(beerServiceImpl.listBeers(null, null, false));

        mockMVC.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }


    @Test
    void testGetBeerById() throws Exception{
        BeerDTO testBeer = beerServiceImpl.listBeers(null, null, false).get(0);

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
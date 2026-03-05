package guru.springframework.spring_7_rest_mvc.controller;

import guru.springframework.spring_7_rest_mvc.entities.Beer;
import guru.springframework.spring_7_rest_mvc.mappers.BeerMapper;
import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import guru.springframework.spring_7_rest_mvc.model.BeerStyle;
import guru.springframework.spring_7_rest_mvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    void testPatchBeerBadName() throws Exception
    {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName","A fantastic tasty new beer with a very very very long name to test validation");

        MvcResult mvcResult =  mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

    }


    @Test
    void testPatchBeerByIdNotFound() {

        assertThrows(NotFoundException.class, () -> beerController.updateBeerPatchById(UUID.randomUUID(), BeerDTO.builder().build()));

    }

    @Transactional
    @Rollback
    @Test
    void testPatchBeerById() {

        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDto = beerMapper.beerToBeerDto(beer);

        beerDto.setId(null);
        beerDto.setVersion(null);
        beerDto.setBeerName("UPDATED BEERNAME");

        String originalBeerName = beer.getBeerName();

        ResponseEntity responseEntity= beerController.updateBeerPatchById(beer.getId(), beerDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findAll().get(0).getBeerName()).isEqualTo(beerDto.getBeerName());
    }


    @Test
    void testDeleteBeerByIdNotFound()
    {
        /*Here it is enough to pass an empty BeerDTO object.*/
        assertThrows(NotFoundException.class, () -> beerController.deleteBeerById(UUID.randomUUID()));

    }

    @Transactional
    @Rollback
    @Test
    void testDeleteBeerById()
    {
        Beer beer = beerRepository.findAll().getFirst();

        ResponseEntity responseEntity = beerController.deleteBeerById(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }

    @Test
    void testUpdateBeerByIdNotFound()
    {
        /*Here it is enough to pass an empty BeerDTO object.*/
        assertThrows(NotFoundException.class, () -> beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build()));

    }

    @Transactional
    @Rollback
    @Test
    void testUpdateBeerById()
    {
        Beer beer = beerRepository.findAll().getFirst();
        BeerDTO beerDto = beerMapper.beerToBeerDto(beer);

        beerDto.setId(null);
        beerDto.setVersion(null);
        final String beerName= "Updated Beer Name";
        beerDto.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(),beerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);

    }

    @Transactional
    @Rollback
    @Test
    void testSaveNewBeer() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Borsodi IPA")
                .beerStyle(BeerStyle.IPA)
                .build();

        ResponseEntity responseEntity = beerController.handlePost(beerDTO);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatusCode.valueOf(201));

        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation()
                .getPath().split("/");

        //The UUID is the 4th element in the array
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }


    @Test
    void testBeerNotFound() {
        assertThrows(NotFoundException.class, () -> {
           beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetBeerById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());

        assertThat(dto).isNotNull();

    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.listBeers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBeers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}
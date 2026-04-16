package guru.springframework.spring_7_rest_mvc.repositories;

import guru.springframework.spring_7_rest_mvc.bootstrap.BootStrapData;
import guru.springframework.spring_7_rest_mvc.entities.Beer;
import guru.springframework.spring_7_rest_mvc.model.BeerStyle;
import guru.springframework.spring_7_rest_mvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootStrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByStyle(){

        BeerStyle beerStyle = BeerStyle.IPA;
        List<Beer> list = beerRepository.findAllByBeerStyle(beerStyle);

        assertThat(list.size()).isEqualTo(548);
    }

    @Test
    void testGetBeerListByName() {

        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(list.size()).isEqualTo(336);

        //Suggested by SonarQube
        //assertThat(list).hasSize(336);

    }

    @Test
    void testSaveBeerBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("Asahi Super Dry with a very very very long name to test validation")
                    .beerStyle(BeerStyle.PORTER)
                    .upc("SomeUpc")
                    .price(new BigDecimal(11.99))
                    .build());

            beerRepository.flush();
        });


    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("Asahi Super Dry")
                        .beerStyle(BeerStyle.PORTER)
                        .upc("SomeUpc")
                        .price(new BigDecimal(11.99))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();

    }

}
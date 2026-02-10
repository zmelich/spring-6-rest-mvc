package guru.springframework.spring_7_rest_mvc.repositories;

import guru.springframework.spring_7_rest_mvc.entities.Beer;
import guru.springframework.spring_7_rest_mvc.entities.Customer;
import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import guru.springframework.spring_7_rest_mvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("Asahi Super Dry")
                .build());

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();

    }

    @Test
    void testDataInitialization()
    {

        Beer beer1 = Beer.builder()
                //.id(UUID.randomUUID())
                //.version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                //.id(UUID.randomUUID())
                //.version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456654323")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                //.id(UUID.randomUUID())
                //.version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("43828282")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerRepository.save(beer1);
        beerRepository.save(beer2);
        beerRepository.save(beer3);

        long numberOfBeers = beerRepository.count();

        assertThat(numberOfBeers).isGreaterThan(0);

        List<Beer> beerList = beerRepository.findAll();

        assertThat(beerList.get(0).getId()).isNotNull();

    }
}
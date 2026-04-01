package guru.springframework.spring_7_rest_mvc.bootstrap;

import guru.springframework.spring_7_rest_mvc.repositories.BeerRepository;
import guru.springframework.spring_7_rest_mvc.repositories.CustomerRepository;
import guru.springframework.spring_7_rest_mvc.services.BeerCsvService;
import guru.springframework.spring_7_rest_mvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootStrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService csvService;

    BootStrapData bootStrapData;

    @BeforeEach
    void setUp() {
        bootStrapData = new BootStrapData(beerRepository, customerRepository,csvService);
    }

    @Test
    void testRun() throws Exception {
        bootStrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);

    }
}
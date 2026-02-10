package guru.springframework.spring_7_rest_mvc.bootstrap;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_7_rest_mvc.entities.Beer;
import guru.springframework.spring_7_rest_mvc.entities.Customer;
import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import guru.springframework.spring_7_rest_mvc.model.BeerStyle;
import guru.springframework.spring_7_rest_mvc.model.CustomerDTO;
import guru.springframework.spring_7_rest_mvc.repositories.BeerRepository;
import guru.springframework.spring_7_rest_mvc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BootStrapData implements CommandLineRunner {


    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    public BootStrapData(BeerRepository beerRepository, CustomerRepository customerRepository) {
        this.beerRepository = beerRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

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

        Customer customer1 = Customer.builder()
                .customerName("John Lennon")
                //.id(UUID.randomUUID())
                //.version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .customerName("Ringo Starr")
                //.id(UUID.randomUUID())
                //.version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .customerName("George Harrison")
                //.id(UUID.randomUUID())
                //.version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

    }
}

package guru.springframework.spring_7_rest_mvc.repositories;

import guru.springframework.spring_7_rest_mvc.entities.Beer;
import guru.springframework.spring_7_rest_mvc.entities.Customer;
import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {

        Customer savedCustomer = customerRepository.save(Customer.builder()
                        .customerName("JPA Data test Customer")
                .build());

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();

    }

    @Test
    void testDataInitialization() {

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

        long numberOfCustomers = customerRepository.count();

        assertThat(numberOfCustomers).isGreaterThan(0);

        List<Customer> customerList = customerRepository.findAll();

        assertThat(customerList.get(0).getId()).isNotNull();

    }
}
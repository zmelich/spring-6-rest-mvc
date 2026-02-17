package guru.springframework.spring_7_rest_mvc.controller;

import guru.springframework.spring_7_rest_mvc.entities.Beer;
import guru.springframework.spring_7_rest_mvc.entities.Customer;
import guru.springframework.spring_7_rest_mvc.mappers.BeerMapper;
import guru.springframework.spring_7_rest_mvc.mappers.CustomerMapper;
import guru.springframework.spring_7_rest_mvc.model.BeerDTO;
import guru.springframework.spring_7_rest_mvc.model.CustomerDTO;
import guru.springframework.spring_7_rest_mvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testDeleteCustomerByIdNotFound() {

        assertThrows(NotFoundException.class, () -> customerController.deleteCustomerById(UUID.randomUUID()));

    }

    @Transactional
    @Rollback
    @Test
    void testDeleteCustomerById() {

        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.count()).isLessThan(3);
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void testPatchCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updateCustomerPatchById(UUID.randomUUID(),CustomerDTO.builder().build()));
    }

    @Test
    void testPatchCustomerById() {

        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        final String updatedCustomerName = "Updated CustomerName";
        customerDTO.setCustomerName(updatedCustomerName);

        ResponseEntity responseEntity = customerController.updateCustomerPatchById(customer.getId(),customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findAll().get(0).getCustomerName()).isEqualTo(updatedCustomerName);

    }

    @Test
    void testUpdateCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build()));

    }

    @Transactional
    @Rollback
    @Test
    void testUpdateCustomerById() {

        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDto = customerMapper.customerToCustomerDto(customer);
        customerDto.setId(null);
        customerDto.setVersion(null);

        final String updatedCustomerName = "Updated CustomerName";
        customerDto.setCustomerName(updatedCustomerName);

        ResponseEntity responseEntity = customerController.updateById(customer.getId(), customerDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findAll().get(0).getCustomerName()).isEqualTo(updatedCustomerName);

    }

    @Transactional
    @Rollback
    @Test
    void testSaveNewCustomer() {

        CustomerDTO newCustomer = CustomerDTO.builder()
                .customerName("Turpi Urfi")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(newCustomer);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatusCode.valueOf(201));

        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation()
                .getPath().split("/");

        //The UUID is the 4th element in the array
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();

        assertThat(customerRepository.count()).isEqualTo(4);

    }

    @Test
    void testGetCustomerById()
    {
        Customer cust = customerRepository.findAll().get(0);
        CustomerDTO dto = customerController.getCustomerById(cust.getId());

        assertThat(dto).isNotNull();
    }
    @Test
    void testGetCustomerByIdNotFound()
    {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }


    @Test
    void testListCustomers()
    {
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyCustomerList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(0);

    }
}
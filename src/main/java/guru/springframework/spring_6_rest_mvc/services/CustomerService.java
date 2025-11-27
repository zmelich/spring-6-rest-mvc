package guru.springframework.spring_6_rest_mvc.services;

import guru.springframework.spring_6_rest_mvc.model.Customer;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface CustomerService {

    List<Customer> listCustomers();

    //Customer getCustomerById(UUID id);
    Optional<Customer> getCustomerById(UUID id);

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID id, Customer customer);

    void deleteCustomerById(UUID id);

    void patchCustomerById(UUID id, Customer customer);
}

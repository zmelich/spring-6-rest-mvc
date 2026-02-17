package guru.springframework.spring_7_rest_mvc.services;

import guru.springframework.spring_7_rest_mvc.model.CustomerDTO;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    //Customer getCustomerById(UUID id);
    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer);

    Boolean deleteCustomerById(UUID id);

    Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customer);
}

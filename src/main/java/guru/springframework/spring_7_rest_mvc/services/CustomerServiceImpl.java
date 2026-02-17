package guru.springframework.spring_7_rest_mvc.services;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_7_rest_mvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .customerName("John Lennon")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .customerName("Ringo Starr")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .customerName("George Harrison")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(),customer1);
        customerMap.put(customer2.getId(),customer2);
        customerMap.put(customer3.getId(),customer3);

    }



    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());

    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {

        log.debug("Get Customer by Id - In service. Id: "+ id);

        return Optional.of(customerMap.get(id));

    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {

        CustomerDTO saveCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName(customer.getCustomerName())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(saveCustomer.getId(),saveCustomer);

        return saveCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);

        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setUpdateDate(LocalDateTime.now());

        //We don't need this line actually - the app is still working without it
        //customerMap.put(existingCustomer.getId(),existingCustomer);

        return Optional.of(existingCustomer);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {

        customerMap.remove(customerId);

        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customer) {

        CustomerDTO existingCust = customerMap.get(id);

        if (StringUtils.hasText(customer.getCustomerName())) {
            existingCust.setCustomerName(customer.getCustomerName());
        }

        return Optional.of(existingCust);

    }


}

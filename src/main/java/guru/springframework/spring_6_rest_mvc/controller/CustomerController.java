package guru.springframework.spring_6_rest_mvc.controller;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_6_rest_mvc.model.Customer;
import guru.springframework.spring_6_rest_mvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
//@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers()
    {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID id){
        log.debug("Get Customer by Id - In controller");

        return customerService.getCustomerById(id);
    }

}

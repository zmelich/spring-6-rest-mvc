package guru.springframework.spring_6_rest_mvc.controller;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_6_rest_mvc.model.Beer;
import guru.springframework.spring_6_rest_mvc.model.Customer;
import guru.springframework.spring_6_rest_mvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
//@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @PatchMapping(value="{customerId}")
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID id, @RequestBody Customer customer){

        customerService.patchCustomerById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(value="{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID id)
    {

        customerService.deleteCustomerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //@PutMapping("{customerId}")
    @PutMapping(value = "{customerId}")
    public ResponseEntity updateById(@PathVariable("customerId") UUID id, @RequestBody Customer customer)
    {
        customerService.updateCustomerById(id,customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Customer customer){

        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/"+savedCustomer.getId().toString());


        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

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

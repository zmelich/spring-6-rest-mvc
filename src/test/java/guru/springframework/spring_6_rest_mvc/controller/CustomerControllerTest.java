package guru.springframework.spring_6_rest_mvc.controller;


import guru.springframework.spring_6_rest_mvc.model.Beer;
import guru.springframework.spring_6_rest_mvc.model.Customer;
import guru.springframework.spring_6_rest_mvc.services.BeerService;
import guru.springframework.spring_6_rest_mvc.services.CustomerService;
import guru.springframework.spring_6_rest_mvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
Created by Zsolt Melich (BT - IVR team)
*/

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMVC;

    //@MockBean -- deprecated
    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    void testListCustomer() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMVC.perform(get("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetCustomerById() throws Exception{
        Customer testCustomer = customerServiceImpl.listCustomers().get(0);

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

        mockMVC.perform(get("/api/v1/customer/" + testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
    }

}

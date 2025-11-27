package guru.springframework.spring_6_rest_mvc.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring_6_rest_mvc.model.Beer;
import guru.springframework.spring_6_rest_mvc.model.Customer;
import guru.springframework.spring_6_rest_mvc.services.BeerService;
import guru.springframework.spring_6_rest_mvc.services.CustomerService;
import guru.springframework.spring_6_rest_mvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;
/*
Created by Zsolt Melich (BT - IVR team)
*/

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMVC;

    @Autowired
    ObjectMapper objectMapper;

    //@MockBean -- deprecated
    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp()
    {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Captor
    ArgumentCaptor<UUID> customerIdArgCaptor;

    @Captor
    ArgumentCaptor<Customer> customerObjectCaptor;


    @Test
    void getCustomerByIdNotFound() throws Exception{

        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMVC.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }


    @Test
    void testPatchExistingCustomer() throws Exception{

        Customer customer = customerServiceImpl.listCustomers().get(0);

        Map<String,Object> customerMap = new HashMap<>();
        customerMap.put("customerName","New Customer Name");

        //mockMVC.perform(patch(CustomerController.CUSTOMER_PATH+"/"+customer.getId())
        mockMVC.perform(patch(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(customerIdArgCaptor.capture(),customerObjectCaptor.capture());

        assertThat(customer.getId()).isEqualTo(customerIdArgCaptor.getValue());
        assertThat(customerMap.get("customerName")).isEqualTo(customerObjectCaptor.getValue().getCustomerName());


    }

    @Test
    void testDeleteExistingCustomer() throws Exception{

        Customer customer = customerServiceImpl.listCustomers().get(0);

        //mockMVC.perform(delete(CustomerController.CUSTOMER_PATH+"/"+customer.getId())
        mockMVC.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //ArgumentCaptor<UUID> customerIdArgCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(customerService).deleteCustomerById(customerIdArgCaptor.capture());

        assertThat(customer.getId()).isEqualTo(customerIdArgCaptor.getValue());
    }

    @Test
    void testUpdateExistingCustomer() throws Exception{

        Customer customer = customerServiceImpl.listCustomers().get(0);

        //mockMVC.perform(put(CustomerController.CUSTOMER_PATH+"/"+ customer.getId())
        mockMVC.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        //verify(customerService).updateCustomerById(any(UUID.class),any(Customer.class));
        //verify(customerService).updateCustomerById(customerIdArgCaptor.capture(),any(Customer.class));
        verify(customerService).updateCustomerById(customerIdArgCaptor.capture(),customerObjectCaptor.capture());

        assertThat(customer.getId()).isEqualTo(customerIdArgCaptor.getValue());

    }


    @Test
    void testCreateNewCustomer() throws Exception {

        Customer newCustomer = customerServiceImpl.listCustomers().get(0);
        newCustomer.setId(null);
        newCustomer.setVersion(null);

        given(customerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMVC.perform(post(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListCustomer() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMVC.perform(get(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetCustomerById() throws Exception{
        Customer testCustomer = customerServiceImpl.listCustomers().get(0);

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

        //mockMVC.perform(get(CustomerController.CUSTOMER_PATH+"/" + testCustomer.getId())
        mockMVC.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
    }

}

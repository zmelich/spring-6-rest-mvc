package guru.springframework.spring_7_rest_mvc.controller;


//import com.fasterxml.jackson.databind.ObjectMapper;
//Reimporting ObjectMapper
import tools.jackson.databind.ObjectMapper;

import guru.springframework.spring_7_rest_mvc.model.CustomerDTO;
import guru.springframework.spring_7_rest_mvc.services.CustomerService;
import guru.springframework.spring_7_rest_mvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//Reimport WebMvcTest
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

//@ExtendWith added for newer version of Spring Boot. Without that intelliJ would not do things like autocomplete for Mockito
@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
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
    ArgumentCaptor<CustomerDTO> customerObjectCaptor;


    @Test
    void getCustomerByIdNotFound() throws Exception{

        //given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);
        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMVC.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }


    @Test
    void testPatchExistingCustomer() throws Exception{

        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        given(customerService.patchCustomerById(any(UUID.class), any(CustomerDTO.class))).willReturn(Optional.of(customer));

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

        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        given(customerService.deleteCustomerById(any(UUID.class))).willReturn(true);

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

        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        given(customerService.updateCustomerById(any(UUID.class), any(CustomerDTO.class))).willReturn(Optional.of(customer));

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

        CustomerDTO newCustomer = customerServiceImpl.listCustomers().get(0);
        newCustomer.setId(null);
        newCustomer.setVersion(null);

        given(customerService.saveNewCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.listCustomers().get(1));

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
        CustomerDTO testCustomer = customerServiceImpl.listCustomers().get(0);

        //given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);
        given(customerService.getCustomerById(testCustomer.getId())).willReturn(Optional.of(testCustomer));

        //mockMVC.perform(get(CustomerController.CUSTOMER_PATH+"/" + testCustomer.getId())
        mockMVC.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
    }

}

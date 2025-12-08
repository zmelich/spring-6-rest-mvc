package guru.springframework.spring_7_rest_mvc.model;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;

//New annotations added for Spring Boot 4 and Spring 7
//@JsonDeserialize
//@JsonProperty()

@JsonDeserialize(builder = Customer.CustomerBuilder.class)
@Builder
@Data
public class Customer {

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}

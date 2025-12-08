package guru.springframework.spring_7_rest_mvc.model;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//New annotations added for Spring Boot 4 and Spring 7
//@JsonDeserialize
//@JsonProperty()

@JsonDeserialize(builder = Beer.BeerBuilder.class)
@Builder
@Data
public class Beer {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("beerName")
    private String beerName;

    @JsonProperty("beerStyle")
    private BeerStyle beerStyle;

    @JsonProperty("upc")
    private String upc;

    @JsonProperty("quantityOnHand")
    private Integer quantityOnHand;

    @JsonProperty("price")
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;


}

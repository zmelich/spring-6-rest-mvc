package guru.springframework.spring_6_rest_mvc.model;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Beer {

    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle bearStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;


}

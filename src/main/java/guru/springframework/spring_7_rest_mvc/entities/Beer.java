package guru.springframework.spring_7_rest_mvc.entities;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_7_rest_mvc.model.BeerStyle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
//@Data - We should not use it with entities
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}

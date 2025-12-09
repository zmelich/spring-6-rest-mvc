package guru.springframework.spring_7_rest_mvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/*
Created by Zsolt Melich (BT - IVR team)
*/

@Builder
//@Data - We should not use it with entities
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private UUID id;

    @Version
    private Integer version;

    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}

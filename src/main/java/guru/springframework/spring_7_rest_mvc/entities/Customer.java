package guru.springframework.spring_7_rest_mvc.entities;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

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

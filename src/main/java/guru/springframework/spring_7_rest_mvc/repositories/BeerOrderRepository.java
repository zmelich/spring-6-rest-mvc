package guru.springframework.spring_7_rest_mvc.repositories;

import guru.springframework.spring_7_rest_mvc.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}

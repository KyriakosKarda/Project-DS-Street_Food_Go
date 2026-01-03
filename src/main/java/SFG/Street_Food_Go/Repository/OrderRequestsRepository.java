package SFG.Street_Food_Go.Repository;

import SFG.Street_Food_Go.Entities.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRequestsRepository extends JpaRepository<OrderRequest, Long> {
    List<OrderRequest> getOrderRequestByRestaurant_Id(Long restaurantId);

    List<OrderRequest> findByPersonId(Long personId);
}

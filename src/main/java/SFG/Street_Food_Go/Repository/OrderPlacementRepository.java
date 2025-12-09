package SFG.Street_Food_Go.Repository;

import SFG.Street_Food_Go.Entities.OrderPlacement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPlacementRepository extends JpaRepository<OrderPlacement, Long> {
}

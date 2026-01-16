package Raipur.Metaliks.example.Raipur.Metaliks.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.TruckDelivery;

@Repository
public interface TruckDeliveryRepository extends JpaRepository<TruckDelivery, Long> {
}

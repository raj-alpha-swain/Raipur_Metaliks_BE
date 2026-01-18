package Raipur.Metaliks.example.Raipur.Metaliks.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Raipur.Metaliks.example.Raipur.Metaliks.Entity.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM buyer")
    List<Buyer> getallBuyers();

    Optional<Buyer> findByName(String name);
}

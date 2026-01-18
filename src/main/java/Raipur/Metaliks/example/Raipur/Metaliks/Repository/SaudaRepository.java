package Raipur.Metaliks.example.Raipur.Metaliks.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.Sauda;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaudaRepository extends JpaRepository<Sauda, Long> {

    @Query("SELECT DISTINCT s.sellerName FROM Sauda s ORDER BY s.sellerName")
    List<String> findDistinctSellerNames();

    List<Sauda> findBySellerName(String sellerName);

    @Query("SELECT s FROM Sauda s WHERE s.saudaDate BETWEEN :startDate AND :endDate")
    List<Sauda> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM Sauda s WHERE s.sellerName = :sellerName AND s.saudaDate BETWEEN :startDate AND :endDate")
    List<Sauda> findBySellerNameAndDateBetween(
            @Param("sellerName") String sellerName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<Sauda> findByBuyerName(String buyerName);
}

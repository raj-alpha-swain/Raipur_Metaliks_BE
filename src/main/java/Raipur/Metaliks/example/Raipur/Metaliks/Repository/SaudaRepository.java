package Raipur.Metaliks.example.Raipur.Metaliks.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.Sauda;

@Repository
public interface SaudaRepository extends JpaRepository<Sauda, Long> {
}

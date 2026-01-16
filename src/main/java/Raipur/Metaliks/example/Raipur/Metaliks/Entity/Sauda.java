package Raipur.Metaliks.example.Raipur.Metaliks.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.TruckDelivery;

@Getter
@Setter
@Entity
@Table(name = "sauda")
public class Sauda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sauda_date")
    private LocalDate saudaDate;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "material")
    private String material;

    @Column(name = "price")
    private Double price;

    @Column(name = "sauda_quantity")
    private Integer saudaQuantity;

    @Column(name = "difference")
    private Integer difference;

    @Column(name = "actual_quantity")
    private Integer actualQuantity;

    @OneToMany(mappedBy = "sauda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TruckDelivery> truckDeliveries = new ArrayList<>();

    // Helper method to add truck delivery
    public void addTruckDelivery(TruckDelivery truckDelivery) {
        truckDeliveries.add(truckDelivery);
        truckDelivery.setSauda(this);
    }

    // Helper method to remove truck delivery
    public void removeTruckDelivery(TruckDelivery truckDelivery) {
        truckDeliveries.remove(truckDelivery);
        truckDelivery.setSauda(null);
    }
}

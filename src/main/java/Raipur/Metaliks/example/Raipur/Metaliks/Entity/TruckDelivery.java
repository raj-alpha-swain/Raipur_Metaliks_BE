package Raipur.Metaliks.example.Raipur.Metaliks.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "truck_delivery")
public class TruckDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "truck_number")
    private String truckNumber;

    @Column(name = "delivery_date")
    private String deliveryDate;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sauda_id")
    @JsonIgnore
    private Sauda sauda;
}
